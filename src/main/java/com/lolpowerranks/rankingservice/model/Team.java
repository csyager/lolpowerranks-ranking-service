package com.lolpowerranks.rankingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Team implements Comparable<Team> {
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("team_acronym")
    private String teamAcronym;
    @JsonProperty("team_name")
    private String teamName;
    @JsonProperty("team_slug")
    private String teamSlug;

    @Override
    public int compareTo(Team o) {
        return teamName.compareTo(o.teamName);
    }
}
