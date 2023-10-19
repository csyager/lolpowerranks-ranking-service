package com.lolpowerranks.rankingservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lolpowerranks.rankingservice.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TeamsResponse {
    @JsonProperty("numTeams")
    private int numTeams;
    @JsonProperty("teams")
    private List<Team> teams;
}
