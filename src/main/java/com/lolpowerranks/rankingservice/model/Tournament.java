package com.lolpowerranks.rankingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Tournament implements Comparable<Tournament>{
    @JsonProperty("tournament_id")
    private String tournamentId;
    @JsonProperty("tournament_name")
    private String tournamentName;

    @Override
    public int compareTo(Tournament o) {
        return tournamentName.compareTo(o.tournamentName);
    }
}
