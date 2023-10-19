package com.lolpowerranks.rankingservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lolpowerranks.rankingservice.model.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TournamentsResponse {
    @JsonProperty("numTournaments")
    private int numTournaments;
    @JsonProperty("tournaments")
    private List<Tournament> tournaments;
}
