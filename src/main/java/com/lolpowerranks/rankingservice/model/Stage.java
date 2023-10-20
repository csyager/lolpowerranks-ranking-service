package com.lolpowerranks.rankingservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Stage implements Comparable<Stage> {
    @JsonProperty("tournament_id")
    private String tournamentId;
    @JsonProperty("stage_name")
    private String stageName;
    @JsonProperty("stage_slug")
    private String stageSlug;
    @JsonProperty("stage_type")
    private String stageType;

    @Override
    public int compareTo(Stage o) {
        return stageName.compareTo(o.stageName);
    }
}
