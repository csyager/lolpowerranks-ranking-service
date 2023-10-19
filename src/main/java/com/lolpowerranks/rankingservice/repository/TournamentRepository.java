package com.lolpowerranks.rankingservice.repository;

import com.lolpowerranks.rankingservice.model.dao.TournamentDAOModel;

import java.util.List;

public interface TournamentRepository {
    List<TournamentDAOModel> getAllTournaments();
    List<String> getTeamsInTournamentStage(String tournamentId, String stage);
}
