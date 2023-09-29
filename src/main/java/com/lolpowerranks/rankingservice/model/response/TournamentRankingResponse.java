package com.lolpowerranks.rankingservice.model.response;

import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TournamentRankingResponse extends RankingResponse {

    @Builder
    public TournamentRankingResponse(List<Ranking> rankings) {
        super(rankings);
    }
}
