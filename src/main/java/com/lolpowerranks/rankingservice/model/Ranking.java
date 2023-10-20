package com.lolpowerranks.rankingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Ranking implements Comparable<Ranking> {
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("team_code")
    private String teamCode;
    @JsonProperty("team_name")
    private String teamName;
    @JsonProperty("team_rank")
    private Integer rank;
    @JsonProperty("team_elo")
    private Double teamElo;

    @Override
    public int compareTo(Ranking r) {
        return Integer.compare(rank, r.getRank());
    }
}
