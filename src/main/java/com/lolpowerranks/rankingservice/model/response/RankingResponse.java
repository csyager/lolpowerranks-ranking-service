package com.lolpowerranks.rankingservice.model.response;

import com.fasterxml.jackson.annotation.JsonValue;
import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RankingResponse {

    @JsonValue
    private List<Ranking> rankings;
}
