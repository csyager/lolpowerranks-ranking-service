package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface RankingsService {

    List<Ranking> getTournamentRanking(@NonNull String tournamentId, Optional<String> stage);

    List<Ranking> getGlobalRanking(@NonNull int numberOfTeams);

    List<Ranking> getTeamRanking(@NonNull String[] teams);

}
