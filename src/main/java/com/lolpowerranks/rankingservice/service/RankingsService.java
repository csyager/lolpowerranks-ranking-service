package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Ranking;
import lombok.NonNull;

import java.util.List;

public interface RankingsService {

    List<Ranking> getTournamentRanking(@NonNull String tournamentId, String stage);

    List<Ranking> getGlobalRanking(@NonNull int numberOfTeams);

    List<Ranking> getTeamRanking(@NonNull String[] teams);

}
