package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Stage;
import com.lolpowerranks.rankingservice.model.Tournament;

import java.util.List;

public interface TournamentsService {

    List<Tournament> getTournaments();
    List<Stage> getTournamentStages(String tournamentId);
}
