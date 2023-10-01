package com.lolpowerranks.rankingservice.repository;

import java.util.List;

public interface TournamentRepository {
    List<String> getTeamsInTournamentStage(String tournamentId, String stage);
}
