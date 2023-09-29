package com.lolpowerranks.rankingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Ranking implements Comparable<Ranking> {
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("team_code")
    private String teamCode;
    @JsonProperty("team_name")
    private String teamName;
    @JsonProperty("team_rank")
    private Integer rank;

    @Override
    public int compareTo(Ranking r) {
        return Integer.compare(rank, r.getRank());
    }
}
