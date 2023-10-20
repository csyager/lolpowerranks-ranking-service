package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Stage;
import com.lolpowerranks.rankingservice.model.Tournament;
import com.lolpowerranks.rankingservice.model.dao.StageDAOModel;
import com.lolpowerranks.rankingservice.model.dao.TournamentDAOModel;
import com.lolpowerranks.rankingservice.repository.StageRepository;
import com.lolpowerranks.rankingservice.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentsServiceImpl implements TournamentsService {

    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private StageRepository stageRepository;

    @Override
    public List<Tournament> getTournaments() {
        List<TournamentDAOModel> tournamentDaos = tournamentRepository.getAllTournaments();
        ArrayList<Tournament> tournaments = new ArrayList<>(tournamentDaos.size());
        tournamentDaos.forEach(tournamentDao -> {
            Tournament tournament = tournamentDao.toTournament();
            tournaments.add(tournament);
        });
        Collections.sort(tournaments);
        return tournaments;
    }

    @Override
    public List<Stage> getTournamentStages(String tournamentId) {
        List<StageDAOModel> stageDaos = stageRepository.getTournamentStages(tournamentId);
        ArrayList<Stage> stages = new ArrayList<>(stageDaos.size());
        stageDaos.forEach(stageDao -> {
            Stage stage = stageDao.toStage();
            stages.add(stage);
        });
        Collections.sort(stages);
        return stages;
    }
}
