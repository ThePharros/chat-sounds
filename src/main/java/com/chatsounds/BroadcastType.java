package com.chatsounds;

public enum BroadcastType
{
    // Social
    PLAYER_JOIN_LEAVE(MatchMode.ANY, " has joined.",
            " has left."),
    CLAN_INVITATION(MatchMode.ANY,
            "has been invited into the"
    ),
    CLAN_EXPULSION(MatchMode.ANY,
            "has expelled"
    ),

    // Loot and collection log
    REGULAR_DROP(MatchMode.ANY,
            "received a drop"
    ),
    RARE_DROP(MatchMode.ANY,
            "received a rare drop"
    ),
    RAID_LOOT(MatchMode.ANY,
            "received special loot from a raid"
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
    LEVEL_UP(MatchMode.ALL,
            "has reached",
            "level"
    ),
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

    // Achievements
    QUEST_COMPLETE(MatchMode.ANY,
            "has completed a quest"
    ),
    ACHIEVEMENT_DIARY(MatchMode.ANY,
            "achievement diary"
    ),
    COMBAT_ACHIEVEMENT(MatchMode.ANY,
            "combat achievement",
            "combat task"
    ),
    PERSONAL_BEST(MatchMode.ANY,
            "achieved a new"
    ),

    // PvP & deaths
    PLAYER_KILL(MatchMode.ANY,
            "has defeated"
    ),
    PLAYER_DEATH(MatchMode.ANY,
            "has been defeated by"
    ),
    HARDCORE_DEATH(MatchMode.ANY,
            "lost their hardcore"
    ),

    // Other
    OTHER(MatchMode.ANY);

    private final MatchMode matchMode;
    private final String[] phrases;

    BroadcastType(MatchMode matchMode, String... phrases)
    {
        this.matchMode = matchMode;
        this.phrases = phrases;
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
                if (!m.contains(phrase))
                {
                    return false;
                }
            }
            return true;
        }
        else // MatchMode.ANY
        {
            for (String phrase : phrases)
            {
                if (m.contains(phrase))
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


