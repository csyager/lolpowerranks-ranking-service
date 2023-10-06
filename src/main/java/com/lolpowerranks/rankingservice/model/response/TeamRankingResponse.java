package com.lolpowerranks.rankingservice.model.response;

import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.Builder;

import java.util.List;

public class TeamRankingResponse extends RankingResponse {

    @Builder
    public TeamRankingResponse (List<Ranking> rankings) {
        super(rankings);
    }
}
