package com.chatsounds;

import java.util.regex.Pattern;

public enum BroadcastType
{
    // Social
    PLAYER_JOIN_LEAVE(MatchMode.ANY,
			"has joined",
            "has left"
	),
    CLAN_INVITATION(MatchMode.ANY,
            "has been invited into the"
    ),
    CLAN_EXPULSION(MatchMode.ANY,
            "has expelled"
    ),

    // Loot and collection log
    RARE_DROP(MatchMode.ANY,
            "received a rare drop"
    ),
    REGULAR_DROP(MatchMode.ANY,
            "received a drop"
    ),
    RAID_LOOT(MatchMode.ANY,
            "received special loot from a raid"
    ),
    CLUE_LOOT(MatchMode.ANY,
            "received a clue item"
    ),

    PET(MatchMode.ANY,
            "has a funny feeling",
            "acquired something special",
            "something weird sneaking into"
    ),
    COLLECTION_LOG(MatchMode.ANY,
            "a new collection log item"
    ),

    // Levels and XP
    COMBAT_LEVEL_UP(MatchMode.ANY,
            "has reached combat level",
            "highest possible combat level"
    ),
    TOTAL_LEVEL_MILESTONE(MatchMode.ANY,
            "has reached a total",
            "total level of",
            "has reached the highest possible total level of"
    ),
    XP_MILESTONE(MatchMode.ALL,
            "has reached",
            "xp in"
    ),
    LEVEL_UP(MatchMode.ALL,
            "has reached",
            "level"
    ),

    // Achievements
    QUEST_COMPLETE(MatchMode.ANY,
            "has completed a quest"
    ),
    ACHIEVEMENT_DIARY(MatchMode.REGEX,
            "has completed the .* diary"
    ),
    COMBAT_ACHIEVEMENT_TIER(MatchMode.REGEX,
            "has unlocked the .* tier of rewards from combat achievements"
    ),

    COMBAT_ACHIEVEMENT_TASK(MatchMode.REGEX,
            "has completed a[n]? .* combat task"
    ),
    PERSONAL_BEST(MatchMode.ANY,
            "achieved a new"
    ),

    // PvP & deaths
    PLAYER_DEATH(MatchMode.ANY,
            "has been defeated by"
    ),
    PLAYER_KILL(MatchMode.ANY,
            "has defeated"
    ),
    HARDCORE_DEATH(MatchMode.ANY,
            "lost their hardcore"
    ),

    // Other
    OTHER(MatchMode.ANY);

    private final MatchMode matchMode;
    private final String[] phrases;
    private final Pattern pattern;

    BroadcastType(MatchMode matchMode, String... phrases)
    {
        this.matchMode = matchMode;
        this.phrases = phrases;
        if (matchMode == MatchMode.REGEX && phrases.length > 0)
        {
            this.pattern = Pattern.compile(phrases[0], Pattern.CASE_INSENSITIVE);
        }
        else
        {
            this.pattern = null;
        }
    }

    public boolean matches(String message)
    {
        if (phrases.length == 0)
        {
            return false;
        }

        String m = message.toLowerCase();

        if (matchMode == MatchMode.ALL)
        {
            for (String phrase : phrases)
            {
                if (!m.contains(phrase.toLowerCase()))
                {
                    return false;
                }
            }
            return true;
        }
        else if (matchMode == MatchMode.REGEX)
        {
            return pattern != null && pattern.matcher(message).find();
        }
        else // MatchMode.ANY
        {
            for (String phrase : phrases)
            {
                if (m.contains(phrase.toLowerCase()))
                {
                    return true;
                }
            }
            return false;
        }
    }

    public static BroadcastType detect(String message)
    {
        for (BroadcastType type : values())
        {
            if (type != OTHER && type.matches(message))
            {
                return type;
            }
        }
        return OTHER;
    }
}
