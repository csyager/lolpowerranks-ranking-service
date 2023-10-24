package com.lolpowerranks.rankingservice.model.response;

import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamRankingResponse extends RankingResponse {

    @Builder
    public TeamRankingResponse (List<Ranking> rankings) {
        super(rankings);
    }
}
