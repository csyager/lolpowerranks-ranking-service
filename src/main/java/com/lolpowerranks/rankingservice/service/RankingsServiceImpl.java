package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import com.lolpowerranks.rankingservice.repository.RankingDAO;
import com.lolpowerranks.rankingservice.repository.RankingRepository;
import com.lolpowerranks.rankingservice.repository.TeamDAO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RankingsServiceImpl implements RankingsService {
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private TeamDAO teamDao;

    @Override
    public List<Ranking> getTournamentRanking(@NonNull String tournamentId, Optional<String> stage) {
        List<RankingDAOModel> teams = rankingRepository.getTeamsBatch(List.of(
                "102787200129022886",
                "109671201259007744",
                "108159964760727567"
        ));
        ArrayList<Ranking> rankings = new ArrayList<>();
        teams.forEach(team -> {
            Ranking ranking = team.toRanking();
            rankings.add(ranking);
        });
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
