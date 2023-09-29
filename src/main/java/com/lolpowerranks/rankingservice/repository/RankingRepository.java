package com.lolpowerranks.rankingservice.repository;

import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;

import java.util.List;

public interface RankingRepository {
    List<RankingDAOModel> getTopNRankedTeams(int topN);
    List<RankingDAOModel> getTeamRankingsBatch(List<String> teamIds);
}
