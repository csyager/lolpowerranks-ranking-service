package com.lolpowerranks.rankingservice.repository;

import com.lolpowerranks.rankingservice.model.dao.StageDAOModel;

import java.util.List;

public interface StageRepository {
    List<StageDAOModel> getTournamentStages(String tournamentId);
}
