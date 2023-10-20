package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import com.lolpowerranks.rankingservice.model.dao.TeamELODAOModel;
import com.lolpowerranks.rankingservice.repository.RankingRepository;
import com.lolpowerranks.rankingservice.repository.TeamELORepository;
import com.lolpowerranks.rankingservice.repository.TournamentRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class RankingsServiceImpl implements RankingsService {
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamELORepository teamELORepository;

    @Override
    public List<Ranking> getTournamentRanking(@NonNull String tournamentId, String stage) {
        List<String> teamIds = tournamentRepository.getTeamsInTournamentStage(tournamentId, stage);
        List<RankingDAOModel> teamRankings = rankingRepository.getTeamRankingsBatch(teamIds);
        Map<String, Double> teamElosMap = getTeamElosMap(teamIds);
        List<Ranking> rankings = teamRankings.stream()
                .map(RankingDAOModel::toRanking)
                .peek(ranking -> ranking.setTeamElo(teamElosMap.get(ranking.getTeamId())))
                .sorted()
                .toList();
        // converts global rank to local (tournament + stage) rank
        for (int i = 1; i < rankings.size() + 1; i++) {
            rankings.get(i - 1).setRank(i);
        }
        return rankings;
    }

    @Override
    public List<Ranking> getGlobalRanking(int numberOfTeams) {
        List<RankingDAOModel> teams = rankingRepository.getTopNRankedTeams(numberOfTeams);
        List<String> teamIds = teams.stream()
                .map(RankingDAOModel::getTeamId).toList();
        Map<String, Double> teamElosMap = getTeamElosMap(teamIds);
        return teams.stream()
                .map(RankingDAOModel::toRanking)
                .peek(ranking -> ranking.setTeamElo(teamElosMap.get(ranking.getTeamId())))
                .sorted()
                .toList();
    }

    @Override
    public List<Ranking> getTeamRanking(@NonNull String[] teamIds) {
        List<RankingDAOModel> teamRankings = rankingRepository.getTeamRankingsBatch(Arrays.asList(teamIds));
        Map<String, Double> teamElosMap = getTeamElosMap(Arrays.asList(teamIds));
        return teamRankings.stream()
                .map(RankingDAOModel::toRanking)
                .peek(ranking -> ranking.setTeamElo(teamElosMap.get(ranking.getTeamId())))
                .sorted().toList();
    }

    private Map<String, Double> getTeamElosMap(List<String> teamIds) {
        List<TeamELODAOModel> teamElos = teamELORepository.getTeamElos(teamIds);
        return teamElos.stream()
                .collect(Collectors.toMap(TeamELODAOModel::getTeamId, TeamELODAOModel::getElo));
    }
}
