package com.lolpowerranks.rankingservice.repository;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository {
    List<String> getTeamsInTournamentStage(String tournamentId, Optional<String> stage);
}
