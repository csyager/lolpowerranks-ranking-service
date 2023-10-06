package com.lolpowerranks.rankingservice.util;

public class Constants {
    public static final int MAX_GLOBAL_RANKINGS = 100;
    public static final int MIN_GLOBAL_RANKINGS = 1;
    public static final String DEFAULT_GLOBAL_RANKINGS = "10";

    // Exception messaging
    public static final String EXCEEDS_MAX_GLOBAL_RANKINGS_MSG = "Requested number of teams must be less than or equal to " + MAX_GLOBAL_RANKINGS;
    public static final String LESS_THAN_MIN_GLOBAL_RANKINGS_MSG = "Requested number of teams must be greater than or equal to " + MIN_GLOBAL_RANKINGS;
    public static final String EMPTY_TEAM_IDS_MSG = "Requested team IDs must not be empty.";
}
