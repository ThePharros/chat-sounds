package com.chatsounds;

import com.google.inject.Provides;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.RuneLite;
import net.runelite.client.audio.AudioPlayer;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Chat Sounds"
)
public class ChatSoundsPlugin extends Plugin
{
	private static final String CS_CHAT_CHANNEL_MSG_1 = "Attempting to join chat-channel...".toLowerCase();
	private static final String CS_CHAT_CHANNEL_MSG_2 = "Now talking in chat-channel ".toLowerCase();
	private static final String CS_CHAT_CHANNEL_MSG_3 = "To talk, start each line of chat with the / symbol.".toLowerCase();
	private static final String CS_CLAN_MSG = "To talk in your clan's channel, start each line of chat with // or /c.".toLowerCase();
	private static final String CS_CLAN_GUEST_MSG_1 = "You are now a guest of ".toLowerCase();
	private static final String CS_CLAN_GUEST_MSG_2 = "To talk, start each line of chat with /// or /gc.".toLowerCase();
	private static final String CS_CLAN_GUEST_MSG_3 = "Attempting to reconnect to guest channel automatically...".toLowerCase();
	private static final String CS_GIM_MSG = "To talk in your Ironman Group's channel, start each line of chat with //// or /g.".toLowerCase();
	private static final String CS_LEAGUES_MSG = "<img=22>";
	private static final Pattern PLAYER_PREFIX =
			Pattern.compile(
					"^.+?\\s+(has|have|been|achieved|acquired|reached|received|lost|unlocked|completed)\\b",
					Pattern.CASE_INSENSITIVE
			);

	private static final File CS_DIR = new File(RuneLite.RUNELITE_DIR.getPath() + File.separator + "chat-sounds");
	private static final File CS_DEFAULT = new File(CS_DIR, "cs_default.wav");
	private static final File CS_PUBLIC = new File(CS_DIR, "cs_public.wav");
	private static final File CS_PRIVATE = new File(CS_DIR, "cs_private.wav");
	private static final File CS_CHAT_CHANNEL = new File(CS_DIR,"cs_chat_channel.wav");
	private static final File CS_CHAT_CHANNEL_BROADCAST = new File(CS_DIR, "cs_chat_channel_broadcast.wav");
	private static final File CS_CLAN = new File(CS_DIR, "cs_clan.wav");
	private static final File CS_CLAN_BROADCAST = new File(CS_DIR, "cs_clan_broadcast.wav");
	private static final File CS_CLAN_GUEST = new File(CS_DIR, "cs_clan_guest.wav");
	private static final File CS_CLAN_GUEST_BROADCAST = new File(CS_DIR, "cs_clan_guest_broadcast.wav");
	private static final File CS_GIM = new File(CS_DIR, "cs_gim.wav");
	private static final File CS_GIM_BROADCAST = new File(CS_DIR, "cs_gim_broadcast.wav");
	private static final File CS_TRADE_REQUEST = new File(CS_DIR, "cs_trade_req.wav");
	private static final File CS_DUEL_REQUEST = new File(CS_DIR, "cs_duel_req.wav");
	private static final File[] CS_FILES = new File[]{
		CS_DEFAULT,
		CS_PUBLIC,
		CS_PRIVATE,
		CS_CHAT_CHANNEL,
		CS_CHAT_CHANNEL_BROADCAST,
		CS_CLAN,
		CS_CLAN_BROADCAST,
		CS_CLAN_GUEST,
		CS_CLAN_GUEST_BROADCAST,
		CS_GIM,
		CS_GIM_BROADCAST,
		CS_TRADE_REQUEST,
		CS_DUEL_REQUEST
	};

	private final AudioPlayer audioPlayer = new AudioPlayer();
	private List<String> allIgnored = new CopyOnWriteArrayList<>();
	private List<String> publicIgnored = new CopyOnWriteArrayList<>();
	private List<String> privateIgnored = new CopyOnWriteArrayList<>();
	private List<String> chatChannelIgnored = new CopyOnWriteArrayList<>();
	private List<String> clanIgnored = new CopyOnWriteArrayList<>();
	private List<String> guestClanIgnored = new CopyOnWriteArrayList<>();
	private List<String> gimIgnored = new CopyOnWriteArrayList<>();
	private List<String> tradeIgnored = new CopyOnWriteArrayList<>();
	private List<String> duelIgnored = new CopyOnWriteArrayList<>();

	@Inject
	private Client client;

	@Inject
	private ChatSoundsConfig config;

	@Override
	protected void startUp()
	{
		initSoundFiles();
		updateLists();
	}

	@Override
	protected void shutDown()
	{
		publicIgnored = null;
		privateIgnored = null;
		chatChannelIgnored = null;
		clanIgnored = null;
		guestClanIgnored = null;
		gimIgnored = null;
		tradeIgnored = null;
		duelIgnored = null;
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		Player player = client.getLocalPlayer();
		String playerName = player != null && player.getName() != null ? player.getName() : "";
		String cleanPlayerName = Text.sanitize(playerName);
		String cleanName = Text.sanitize(chatMessage.getName());
		ChatMessageType type = chatMessage.getType();

		String msg = Text.standardize(chatMessage.getMessage());
		String cleanMsg = Text.removeTags(msg);
		String cleanBroadcastName = Text.sanitize(extractBroadcastPlayerName(cleanMsg));
		String strippedMsg = stripPlayerName(cleanMsg);

		// Turn off sounds for yourself or when not logged in.
		if (player == null ||
				client.getGameState() != GameState.LOGGED_IN ||
				cleanName.equalsIgnoreCase(cleanPlayerName) ||
				cleanBroadcastName.equalsIgnoreCase(cleanPlayerName)) {
			return;
		}

		// Turn off sounds for global settings.
		if (shouldIgnorePlayer(allIgnored, cleanName) || shouldIgnorePlayer(allIgnored, cleanBroadcastName) ||
				config.allChats() == GlobalSoundsMode.ON) {
			return;
		}

		// Check for the various chat types in the game.
		switch (type) {
			case MODCHAT:
			case PUBLICCHAT:
				if (shouldIgnorePlayer(publicIgnored, cleanName)) {
					return;
				}
				playSound(config.publicChat(), CS_PUBLIC, config.publicVolume());
				break;

			case MODPRIVATECHAT:
			case PRIVATECHAT:
				if (shouldIgnorePlayer(privateIgnored, cleanName)) {
					return;
				}
				playSound(config.privateChat(), CS_PRIVATE, config.privateVolume());
				break;

			case FRIENDSCHAT:
				if (shouldIgnorePlayer(chatChannelIgnored, cleanName)) {
					return;
				}
				playSound(config.chatChannel(), CS_CHAT_CHANNEL, config.chatChannelVolume());
				break;

			case FRIENDSCHATNOTIFICATION:
				BroadcastType chatChannelBroadcastType = BroadcastType.detect(strippedMsg);
				if (shouldIgnorePlayer(chatChannelIgnored, cleanBroadcastName)) {
					return;
				}
				if (chatChannelBroadcastType == BroadcastType.PLAYER_JOIN_LEAVE) {
					if (config.chatChannelJoinLeave()) {
						playSound(config.chatChannelBroadcast(), CS_CHAT_CHANNEL_BROADCAST, config.chatChannelVolume());
					}
					return;
				}
				else if (!msg.equals(CS_CHAT_CHANNEL_MSG_1) && !msg.startsWith(CS_CHAT_CHANNEL_MSG_2) &&
						!msg.equals(CS_CHAT_CHANNEL_MSG_3)) {
					playSound(config.chatChannelBroadcast(), CS_CHAT_CHANNEL_BROADCAST, config.chatChannelVolume());
				}
				break;

			case CLAN_CHAT:
				if (shouldIgnorePlayer(clanIgnored, cleanName)) {
					return;
				}
				playSound(config.clanChat(), CS_CLAN, config.clanVolume());
				break;

			case CLAN_MESSAGE:
				if (msg.contains(CS_LEAGUES_MSG) && !config.clanLeagues()) {
					return;
				}
				BroadcastType clanBroadcastType = BroadcastType.detect(strippedMsg);
				if (shouldIgnorePlayer(clanIgnored, cleanBroadcastName)) {
					return;
				}
				if (shouldAlertClanBroadcastType(clanBroadcastType) && !msg.equals(CS_CLAN_MSG)) {
					playSound(config.clanBroadcast(), CS_CLAN_BROADCAST, config.clanVolume());
				}
				break;

			case CLAN_GUEST_CHAT:
				if (shouldIgnorePlayer(guestClanIgnored, cleanName)) {
					return;
				}
				playSound(config.guestClanChat(), CS_CLAN_GUEST, config.guestClanVolume());
				break;

			case CLAN_GUEST_MESSAGE:
				// Guest clan only has join/leave broadcasts afaik?
				BroadcastType guestBroadcastType = BroadcastType.detect(strippedMsg);
				if (shouldIgnorePlayer(guestClanIgnored, cleanBroadcastName)) {
					return;
				}
				if (guestBroadcastType == BroadcastType.PLAYER_JOIN_LEAVE && config.guestClanJoinLeave() &&
						!msg.equals(CS_CLAN_GUEST_MSG_1) && !msg.startsWith(CS_CLAN_GUEST_MSG_2) && !msg.equals(CS_CLAN_GUEST_MSG_3)) {
					playSound(config.guestClanBroadcast(), CS_CLAN_GUEST_BROADCAST, config.guestClanVolume());
				}
				break;

			case CLAN_GIM_CHAT:
				if (shouldIgnorePlayer(gimIgnored, cleanName)) {
					return;
				}
				playSound(config.gimChat(), CS_GIM, config.groupIronVolume());
				break;

			case CLAN_GIM_MESSAGE:
				if (msg.contains(CS_LEAGUES_MSG) && !config.gimLeagues()) {
					return;
				}
				BroadcastType gimBroadcastType = BroadcastType.detect(strippedMsg);
				if (shouldIgnorePlayer(gimIgnored, cleanBroadcastName)) {
					return;
				}
				if (shouldAlertGimBroadcastType(gimBroadcastType) && !msg.equals(CS_GIM_MSG)) {
					playSound(config.gimBroadcast(), CS_GIM_BROADCAST, config.groupIronVolume());
				}
				break;

			case TRADEREQ:
				if (shouldIgnorePlayer(tradeIgnored, cleanName)) {
					return;
				}
				playSound(config.tradeRequest(), CS_TRADE_REQUEST, config.tradeVolume());
				break;

			case CHALREQ_TRADE:
				if (shouldIgnorePlayer(duelIgnored, cleanName)) {
					return;
				}
				playSound(config.duelRequest(), CS_DUEL_REQUEST, config.duelVolume());
				break;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged)
	{
		if (configChanged.getGroup().equals("chatSounds"))
		{
			updateLists();
		}
	}

	private String stripPlayerName(String message)
	{
		Matcher m = PLAYER_PREFIX.matcher(message);
		if (m.find())
		{
			return message.substring(m.start(1));
		}
		return message;
	}

	private String extractBroadcastPlayerName(String message)
	{
		Matcher m = PLAYER_PREFIX.matcher(message);
		if (m.find())
		{
			return message.substring(0, m.start(1)).trim();
		}
		return "";
	}

	// Returns true if the message is from an ignored player in the chat's type.
	private boolean shouldIgnorePlayer(List<String> ignoreList, String name) {
		return ignoreList.stream().anyMatch(s -> s.equalsIgnoreCase(name));
    }

	private boolean shouldAlertClanBroadcastType(BroadcastType type)
	{
		switch (type)
		{
			case PLAYER_JOIN_LEAVE:
				return config.clanJoinLeave();
			case CLAN_INVITATION:
				return config.clanNewMember();
			case CLAN_EXPULSION:
				return config.clanExpelledMember();
			case RARE_DROP:
				return config.clanRareDrop();
			case RAID_LOOT:
				return config.clanRaidLoot();
			case REGULAR_DROP:
				return config.clanRegularDrop();
			case CLUE_LOOT:
				return config.clanClueLoot();
			case PET:
				return config.clanPetDrop();
			case COLLECTION_LOG:
				return config.clanCollectionLog();
			case LEVEL_UP:
				return config.clanLevelUp();
			case COMBAT_LEVEL_UP:
				return config.clanCombatLevel();
			case TOTAL_LEVEL_MILESTONE:
				return config.clanTotalLevelMilestone();
			case XP_MILESTONE:
				return config.clanXpMilestone();
			case QUEST_COMPLETE:
				return config.clanQuest();
			case ACHIEVEMENT_DIARY:
				return config.clanDiary();
			case COMBAT_ACHIEVEMENT_TIER:
				return config.clanCombatAchievementTier();
			case COMBAT_ACHIEVEMENT_TASK:
				return config.clanCombatAchievementTask();
			case PERSONAL_BEST:
				return config.clanPersonalBest();
			case PLAYER_KILL:
				return config.clanPvpKill();
			case PLAYER_DEATH:
				return config.clanPvpDeath();
			case HARDCORE_DEATH:
				return config.clanHardcoreDeath();
			default:
				return false;
		}
	}

	private boolean shouldAlertGimBroadcastType(BroadcastType type)
	{
		switch (type)
		{
			case RARE_DROP:
				return config.gimRareDrop();
			case RAID_LOOT:
				return config.gimRaidLoot();
			case REGULAR_DROP:
				return config.gimRegularDrop();
			case CLUE_LOOT:
				return config.gimClueLoot();
			case PET:
				return config.gimPetDrop();
			case COLLECTION_LOG:
				return config.gimCollectionLog();
			case LEVEL_UP:
				return config.gimLevelUp();
			case COMBAT_LEVEL_UP:
				return config.gimCombatLevel();
			case TOTAL_LEVEL_MILESTONE:
				return config.gimTotalLevelMilestone();
			case XP_MILESTONE:
				return config.gimXpMilestone();
			case QUEST_COMPLETE:
				return config.gimQuest();
			case ACHIEVEMENT_DIARY:
				return config.gimDiary();
			case COMBAT_ACHIEVEMENT_TIER:
				return config.gimCombatAchievementTier();
			case COMBAT_ACHIEVEMENT_TASK:
				return config.gimCombatAchievementTask();
			case PERSONAL_BEST:
				return config.gimPersonalBest();
			case PLAYER_KILL:
				return config.gimPvpKill();
			case PLAYER_DEATH:
				return config.gimPvpDeath();
			default:
				return false;
		}
	}

	private void initSoundFiles()
	{
		if (!CS_DIR.exists())
		{
			CS_DIR.mkdirs();
		}

		for (File f : CS_FILES)
		{
			try
			{
				if (f.exists()) {
					continue;
				}
				InputStream stream = ChatSoundsPlugin.class.getClassLoader().getResourceAsStream("cs_default.wav");
				OutputStream out = new FileOutputStream(f);
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = stream.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
				out.close();
				stream.close();
			}  catch (Exception e) {
				log.info("ChatSoundsPlugin - " + e + ": " + f);
			}
		}
	}

	private void playSound(ChatSoundsMode mode, File f, int volume)
	{
		if (mode == ChatSoundsMode.OFF || !f.exists())
		{
			return;
		}

		try
		{
			f = mode.equals(ChatSoundsMode.DEFAULT) ? CS_DEFAULT : f;
			audioPlayer.play(f, linearTodB(volume));
		}
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException e)
		{
			log.warn("ChatSoundsPlugin::playSound() error!", e);
		}
	}

	private float linearTodB(int linearVolume)
	{
		return 20.0f * (float) Math.log10(linearVolume/100.0f);
	}

	private void updateLists()
	{
		allIgnored = Text.fromCSV(config.allChatsIgnorePlayersList());
		publicIgnored = Text.fromCSV(config.publicIgnorePlayersList());
		privateIgnored = Text.fromCSV(config.privateIgnorePlayersList());
		chatChannelIgnored = Text.fromCSV(config.chatChannelIgnorePlayersList());
		clanIgnored = Text.fromCSV(config.clanIgnorePlayersList());
		guestClanIgnored = Text.fromCSV(config.guestClanIgnorePlayersList());
		gimIgnored = Text.fromCSV(config.groupIronIgnorePlayersList());
		tradeIgnored = Text.fromCSV(config.tradeIgnorePlayersList());
		duelIgnored = Text.fromCSV(config.duelIgnorePlayersList());
	}

	@Provides
	ChatSoundsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ChatSoundsConfig.class);
	}
}
