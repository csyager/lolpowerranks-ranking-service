package com.lolpowerranks.rankingservice.model.response;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor

public class RankingResponse {

    @JsonValue
    private List<Ranking> rankings;
}
