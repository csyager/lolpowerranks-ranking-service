package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import com.lolpowerranks.rankingservice.repository.RankingRepository;
import com.lolpowerranks.rankingservice.repository.TeamDAO;
import com.lolpowerranks.rankingservice.repository.TournamentRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class RankingsServiceImpl implements RankingsService {
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamDAO teamDao;

    @Override
    public List<Ranking> getTournamentRanking(@NonNull String tournamentId, String stage) {
        List<String> teamIds = tournamentRepository.getTeamsInTournamentStage(tournamentId, stage);
        List<RankingDAOModel> teamRankings = rankingRepository.getTeamRankingsBatch(teamIds);
        List<Ranking> rankings = teamRankings.stream()
                .map(RankingDAOModel::toRanking).sorted().collect(Collectors.toList());
        // converts global rank to local (tournament + stage) rank
        for (int i = 1; i < rankings.size() + 1; i++) {
            rankings.get(i - 1).setRank(i);
        }
        return rankings;
    }

    @Override
    public List<Ranking> getGlobalRanking(int numberOfTeams) {
        List<RankingDAOModel> teams = rankingRepository.getTopNRankedTeams(numberOfTeams);
        ArrayList<Ranking> rankings = new ArrayList<>(numberOfTeams);
        teams.forEach(team -> {
            Ranking ranking = team.toRanking();
            rankings.add(ranking);
        });
        Collections.sort(rankings);
        return rankings;
    }

    @Override
    public List<Ranking> getTeamRanking(@NonNull String[] teams) {
        return null;
    }
}
