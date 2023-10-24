package com.lolpowerranks.rankingservice.model.response;

import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GlobalRankingResponse extends RankingResponse {

    @Builder
    public GlobalRankingResponse(List<Ranking> rankings) {
        super(rankings);
    }
}
