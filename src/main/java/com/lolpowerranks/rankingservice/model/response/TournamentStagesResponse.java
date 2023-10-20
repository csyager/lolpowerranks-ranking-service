package com.lolpowerranks.rankingservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lolpowerranks.rankingservice.model.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TournamentStagesResponse {
    @JsonProperty("tournament_id")
    private String tournamentId;
    @JsonProperty("num_stages")
    private int numStages;
    @JsonProperty("stages")
    private List<Stage> stages;
}
