package com.chatsounds;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("chatSounds")
public interface ChatSoundsConfig extends Config
{
	/**************
	 * GLOBAL SETTINGS *
	 **************/
	@ConfigSection(
			name = "Global Settings",
			description = "Settings for all chats.",
			position = 0
	)
	String globalList = "globalList";

	@ConfigItem(
			keyName = "allChats",
			name = "Global Mute",
			description = "Turns all sound effects either on or off.",
			position = 1,
			section = globalList
	)
	default GlobalSoundsMode allChats()
	{
		return GlobalSoundsMode.OFF;
	}

	@ConfigItem(
			keyName = "allChatsIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 2,
			section = globalList
	)
	default String allChatsIgnorePlayersList()
	{
		return "";
	}

	/**************
	* PUBLIC CHAT *
 	**************/
	@ConfigSection(
			name = "Public Chat",
			description = "Settings for public chat.",
			position = 3
	)
	String publicList = "publicList";

	@ConfigItem(
		keyName = "publicChat",
		name = "Public Chat",
		description = "The sound effect to use when receiving a public chat message.",
		position = 4,
		section = publicList
	)
	default ChatSoundsMode publicChat()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "publicVolume",
			name = "Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 5,
			section = publicList
	)
	default int publicVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "publicIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 6,
			section = publicList
	)
	default String publicIgnorePlayersList()
	{
		return "";
	}

	/***************
	* PRIVATE CHAT *
	****************/
	@ConfigSection(
			name = "Private Chat",
			description = "Settings for private chat.",
			position = 7
	)
	String privateList = "privateList";

	@ConfigItem(
		keyName = "privateChat",
		name = "Private Chat",
		description = "The sound effect to use when receiving a private chat message.",
		position = 8,
		section = privateList
	)
	default ChatSoundsMode privateChat()
	{
		return ChatSoundsMode.DEFAULT;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "privateVolume",
			name = "Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 9,
			section = privateList
	)
	default int privateVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "privateIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 10,
			section = privateList
	)
	default String privateIgnorePlayersList()
	{
		return "";
	}

	/****************
 	* CHAT-CHANNELS *
	****************/
	@ConfigSection(
			name = "Chat-channel",
			description = "Settings for chat-channels.",
			position = 11
	)
	String chatChannelList = "chatChannelList";

	@ConfigItem(
		keyName = "chatChannel",
		name = "Chat-channel",
		description = "The sound effect to use when receiving a chat-channel chat message.",
		position = 12,
		section = chatChannelList
	)
	default ChatSoundsMode chatChannel()
	{
		return ChatSoundsMode.OFF;
	}

	@ConfigItem(
			keyName = "chatChannelBroadcast",
			name = "Chat-channel Broadcast",
			description = "The sound effect to use when receiving a chat-channel broadcast message.",
			position = 13,
			section = chatChannelList
	)
	default ChatSoundsMode chatChannelBroadcast()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "chatChannelVolume",
			name = "Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 14,
			section = chatChannelList
	)
	default int chatChannelVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "chatChannelIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 15,
			section = chatChannelList
	)
	default String chatChannelIgnorePlayersList()
	{
		return "";
	}

	@ConfigItem(
			keyName = "chatChannelJoinLeave",
			name = "Join/leave broadcasts",
			description = "Play a sound for users joining or leaving the chat-channel.",
			position = 16,
			section = chatChannelList
	)
	default boolean chatChannelJoinLeave()
	{
		return false;
	}

	/*************
 	* CLAN CHATS *
 	*************/
	@ConfigSection(
			name = "Clan Chat",
			description = "Settings for clan chats.",
			position = 17
	)
	String clanChatList = "clanChatList";

	@ConfigItem(
		keyName = "clanChat",
		name = "Clan Chat",
		description = "The sound effect to use when receiving a clan chat message.",
		position = 18,
		section = clanChatList
	)
	default ChatSoundsMode clanChat()
	{
		return ChatSoundsMode.OFF;
	}

	@ConfigItem(
		keyName = "clanBroadcast",
		name = "Clan Broadcast",
		description = "The sound effect to use when receiving a clan broadcast message.",
		position = 19,
		section = clanChatList
	)
	default ChatSoundsMode clanBroadcast()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "clanVolume",
			name = "Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 20,
			section = clanChatList
	)
	default int clanVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "clanIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 21,
			section = clanChatList
	)
	default String clanIgnorePlayersList()
	{
		return "";
	}

	@ConfigItem(
			keyName = "clanJoinLeave",
			name = "Join/leave broadcasts",
			description = "Play a sound for users joining or leaving the clan chat.",
			position = 22,
			section = clanChatList
	)
	default boolean clanJoinLeave()
	{
		return false;
	}

	@ConfigSection(
			name = "Clan Chat Toggles",
			description = "Individual toggles for clan chats.",
			position = 23,
			closedByDefault = true
	)
	String clanChatToggles = "clanChatToggles";

	@ConfigItem(
			keyName = "clanNewMember",
			name = "Clan invitation broadcasts",
			description = "Play a sound for new players joining the clan.",
			position = 24,
			section = clanChatToggles
	)
	default boolean clanNewMember()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanExpelledMember",
			name = "Clan expulsion broadcasts",
			description = "Play a sound for clan members being expelled from the clan.",
			position = 25,
			section = clanChatToggles
	)
	default boolean clanExpelledMember()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanRegularDrop",
			name = "Regular drop broadcasts",
			description = "Play a sound for users receiving a regular drop.",
			position = 26,
			section = clanChatToggles
	)
	default boolean clanRegularDrop()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanRareDrop",
			name = "Rare drop broadcasts",
			description = "Play a sound for users receiving a rare drop.",
			position = 27,
			section = clanChatToggles
	)
	default boolean clanRareDrop()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanRaidLoot",
			name = "Raid loot broadcasts",
			description = "Play a sound for users receiving raid loot.",
			position = 28,
			section = clanChatToggles
	)
	default boolean clanRaidLoot()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanClueLoot",
			name = "Clue loot broadcasts",
			description = "Play a sound for users receiving clue loot.",
			position = 29,
			section = clanChatToggles
	)
	default boolean clanClueLoot()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanPetDrop",
			name = "Pet drop broadcasts",
			description = "Play a sound for users receiving a pet drop.",
			position = 30,
			section = clanChatToggles
	)
	default boolean clanPetDrop()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanCollectionLog",
			name = "Collection log broadcasts",
			description = "Play a sound for users receiving a new collection log item.",
			position = 31,
			section = clanChatToggles
	)
	default boolean clanCollectionLog()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanLevelUp",
			name = "Level up broadcasts",
			description = "Play a sound for users leveling up.",
			position = 32,
			section = clanChatToggles
	)
	default boolean clanLevelUp()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanCombatLevel",
			name = "Combat level broadcasts",
			description = "Play a sound for users gaining a combat level.",
			position = 33,
			section = clanChatToggles
	)
	default boolean clanCombatLevel()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanTotalLevelMilestone",
			name = "Total level broadcasts",
			description = "Play a sound for users reaching a new total level milestone.",
			position = 34,
			section = clanChatToggles
	)
	default boolean clanTotalLevelMilestone()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanXpMilestone",
			name = "XP milestone broadcasts",
			description = "Play a sound for users reaching an XP milestone.",
			position = 35,
			section = clanChatToggles
	)
	default boolean clanXpMilestone()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanQuest",
			name = "Quest broadcasts",
			description = "Play a sound for clan members completing a quest.",
			position = 36,
			section = clanChatToggles
	)
	default boolean clanQuest()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanDiary",
			name = "Achievement diary broadcasts",
			description = "Play a sound for clan members completing an achievement diary task.",
			position = 37,
			section = clanChatToggles
	)
	default boolean clanDiary()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanCombatAchievementTier",
			name = "Combat achievement tier broadcasts",
			description = "Play a sound for clan members completing a combat achievement tier.",
			position = 38,
			section = clanChatToggles
	)
	default boolean clanCombatAchievementTier()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanCombatAchievementTask",
			name = "Combat achievement task broadcasts",
			description = "Play a sound for clan members completing a combat achievement task.",
			position = 39,
			section = clanChatToggles
	)
	default boolean clanCombatAchievementTask()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanPersonalBest",
			name = "Personal best broadcasts",
			description = "Play a sound for users achieving a new personal best.",
			position = 40,
			section = clanChatToggles
	)
	default boolean clanPersonalBest()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanPvpKill",
			name = "PvP kill broadcasts",
			description = "Play a sound for clan members killing another player.",
			position = 41,
			section = clanChatToggles
	)
	default boolean clanPvpKill()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanPvpDeath",
			name = "PvP death broadcasts",
			description = "Play a sound for clan members dying to another player.",
			position = 42,
			section = clanChatToggles
	)
	default boolean clanPvpDeath()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanHardcoreDeath",
			name = "Hardcore death broadcasts",
			description = "Play a sound for hardcore ironmen dying.",
			position = 43,
			section = clanChatToggles
	)
	default boolean clanHardcoreDeath()
	{
		return true;
	}

	@ConfigItem(
			keyName = "clanLeagues",
			name = "Leagues broadcasts",
			description = "Include leagues broadcasts.",
			position = 44,
			section = clanChatToggles
	)
	default boolean clanLeagues()
	{
		return true;
	}

	/**************
 	* GUEST CLANS *
	**************/
	@ConfigSection(
			name = "Guest Clan Chat",
			description = "Settings for guest clan chats.",
			position = 45
	)
	String guestClanList = "guestClanList";

	@ConfigItem(
			keyName = "guestClanChat",
			name = "Guest Clan Chat",
			description = "The sound effect to use when receiving a guest clan chat message.",
			position = 46,
			section = guestClanList
	)
	default ChatSoundsMode guestClanChat()
	{
		return ChatSoundsMode.OFF;
	}

	@ConfigItem(
			keyName = "guestClanBroadcast",
			name = "Guest Clan Broadcast",
			description = "The sound effect to use when receiving a guest clan broadcast message.",
			position = 47,
			section = guestClanList
	)
	default ChatSoundsMode guestClanBroadcast()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "guestClanChannelVolume",
			name = "Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 48,
			section = guestClanList
	)
	default int guestClanVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "guestClanJoinLeave",
			name = "Join/leave broadcasts",
			description = "Play a sound for users joining or leaving the guest clan chat.",
			position = 49,
			section = guestClanList
	)
	default boolean guestClanJoinLeave()
	{
		return true;
	}

	@ConfigItem(
			keyName = "guestClanIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 50,
			section = guestClanList
	)
	default String guestClanIgnorePlayersList()
	{
		return "";
	}

	/****************
 	* GROUP IRONMAN *
	****************/
	@ConfigSection(
			name = "Group Ironman Chat",
			description = "Settings for group ironman chats.",
			position = 51
	)
	String groupList = "groupList";

	@ConfigItem(
		keyName = "gimChat",
		name = "Group Ironman Chat",
		description = "The sound effect to use when receiving a group ironman chat message.",
		position = 52,
		section = groupList
	)
	default ChatSoundsMode gimChat()
	{
		return ChatSoundsMode.OFF;
	}

	@ConfigItem(
		keyName = "gimBroadcast",
		name = "Group Ironman Broadcast",
		description = "The sound effect to use when receiving a group ironman broadcast message.",
		position = 53,
		section = groupList
	)
	default ChatSoundsMode gimBroadcast()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "groupIronVolume",
			name = "Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 54,
			section = groupList
	)
	default int groupIronVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "groupIronIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 55,
			section = groupList
	)
	default String groupIronIgnorePlayersList()
	{
		return "";
	}

	@ConfigSection(
			name = "Group Ironman Toggles",
			description = "Individual toggles for group ironman.",
			position = 56,
			closedByDefault = true
	)
	String groupIronmanToggles = "groupIronmanToggles";

	@ConfigItem(
			keyName = "gimRegularDrop",
			name = "Regular drop broadcasts",
			description = "Play a sound for group members receiving a regular drop.",
			position = 57,
			section = groupIronmanToggles
	)
	default boolean gimRegularDrop()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimRareDrop",
			name = "Rare drop broadcasts",
			description = "Play a sound for group members receiving a rare drop.",
			position = 58,
			section = groupIronmanToggles
	)
	default boolean gimRareDrop()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimRaidLoot",
			name = "Raid loot broadcasts",
			description = "Play a sound for group members receiving raid loot.",
			position = 59,
			section = groupIronmanToggles
	)
	default boolean gimRaidLoot()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimClueLoot",
			name = "Clue loot broadcasts",
			description = "Play a sound for group members receiving clue loot.",
			position = 60,
			section = groupIronmanToggles
	)
	default boolean gimClueLoot()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimPetDrop",
			name = "Pet drop broadcasts",
			description = "Play a sound for group members receiving a pet drop.",
			position = 61,
			section = groupIronmanToggles
	)
	default boolean gimPetDrop()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimCollectionLog",
			name = "Collection log broadcasts",
			description = "Play a sound for group members receiving a new collection log item.",
			position = 62,
			section = groupIronmanToggles
	)
	default boolean gimCollectionLog()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimLevelUp",
			name = "Level up broadcasts",
			description = "Play a sound for group members leveling up.",
			position = 63,
			section = groupIronmanToggles
	)
	default boolean gimLevelUp()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimCombatLevel",
			name = "Combat level broadcasts",
			description = "Play a sound for group members gaining a combat level.",
			position = 64,
			section = groupIronmanToggles
	)
	default boolean gimCombatLevel()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimTotalLevelMilestone",
			name = "Total level broadcasts",
			description = "Play a sound for group members reaching a new total level milestone.",
			position = 65,
			section = groupIronmanToggles
	)
	default boolean gimTotalLevelMilestone()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimXpMilestone",
			name = "XP milestone broadcasts",
			description = "Play a sound for group members reaching an XP milestone.",
			position = 66,
			section = groupIronmanToggles
	)
	default boolean gimXpMilestone()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimQuest",
			name = "Quest broadcasts",
			description = "Play a sound for group members completing a quest.",
			position = 67,
			section = groupIronmanToggles
	)
	default boolean gimQuest()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimDiary",
			name = "Achievement diary broadcasts",
			description = "Play a sound for group members completing an achievement diary task.",
			position = 68,
			section = groupIronmanToggles
	)
	default boolean gimDiary()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimCombatAchievementTier",
			name = "Combat achievement tier broadcasts",
			description = "Play a sound for group members completing a combat achievement tier.",
			position = 69,
			section = groupIronmanToggles
	)
	default boolean gimCombatAchievementTier()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimCombatAchievementTask",
			name = "Combat achievement task broadcasts",
			description = "Play a sound for group members completing a combat achievement task.",
			position = 70,
			section = groupIronmanToggles
	)
	default boolean gimCombatAchievementTask()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimPersonalBest",
			name = "Personal best broadcasts",
			description = "Play a sound for group members achieving a new personal best.",
			position = 71,
			section = groupIronmanToggles
	)
	default boolean gimPersonalBest()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimPvpKill",
			name = "PvP kill broadcasts",
			description = "Play a sound for group members killing another player.",
			position = 72,
			section = groupIronmanToggles
	)
	default boolean gimPvpKill()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimPvpDeath",
			name = "PvP death broadcasts",
			description = "Play a sound for group members dying to another player.",
			position = 73,
			section = groupIronmanToggles
	)
	default boolean gimPvpDeath()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gimLeagues",
			name = "Leagues broadcasts",
			description = "Include leagues broadcasts.",
			position = 74,
			section = groupIronmanToggles
	)
	default boolean gimLeagues()
	{
		return true;
	}

	/*********
	* TRADES *
	*********/
	@ConfigSection(
			name = "Trade Requests",
			description = "Settings for trade requests.",
			position = 75
	)
	String tradeList = "tradeList";

	@ConfigItem(
			keyName = "tradeRequest",
			name = "Trade Request",
			description = "The sound effect to use when receiving a trade request.",
			position = 76,
			section = tradeList
	)
	default ChatSoundsMode tradeRequest()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "tradeVolume",
			name = "Trade Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 77,
			section = tradeList
	)
	default int tradeVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "tradeIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 78,
			section = tradeList
	)
	default String tradeIgnorePlayersList()
	{
		return "";
	}

	/********
	* DUELS *
	********/
	@ConfigSection(
			name = "Duel Requests",
			description = "Settings for duel requests.",
			position = 79
	)
	String duelList = "duelList";

	@ConfigItem(
			keyName = "duelRequest",
			name = "Duel Request",
			description = "The sound effect to use when receiving a duel request.",
			position = 80,
			section = duelList
	)
	default ChatSoundsMode duelRequest()
	{
		return ChatSoundsMode.OFF;
	}

	@Range(
			max = 100
	)
	@Units(
			value = "%"
	)
	@ConfigItem(
			keyName = "duelVolume",
			name = "Duel Volume",
			description = "Sets the volume of the chat message sound effect.",
			position = 81,
			section = duelList
	)
	default int duelVolume()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "duelIgnorePlayers",
			name = "Ignore player names",
			description = "A comma-separated list of player names to never play a sound for.",
			position = 82,
			section = duelList
	)
	default String duelIgnorePlayersList()
	{
		return "";
	}
}
