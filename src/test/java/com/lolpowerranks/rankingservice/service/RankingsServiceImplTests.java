package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import com.lolpowerranks.rankingservice.model.dao.TeamELODAOModel;
import com.lolpowerranks.rankingservice.repository.RankingRepository;
import com.lolpowerranks.rankingservice.repository.TeamELORepository;
import com.lolpowerranks.rankingservice.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class RankingsServiceImplTests {

    @Mock
    private RankingRepository rankingRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private TeamELORepository teamEloRepository;

    @InjectMocks
    private RankingsServiceImpl rankingService;

    private static final int ALL_RANKINGS_SIZE = 10;
    private static final int ALL_TEAM_IDS_SIZE = 10;
    private static final int SELECT_TEAM_IDS_SIZE = 3;

    private static List<RankingDAOModel> allRankingModels;
    private static List<String> allTeamIds;
    private static List<String> selectTeamIds;

    @BeforeAll
    static void init() {
        allRankingModels = new ArrayList<>(ALL_RANKINGS_SIZE);
        allTeamIds = new ArrayList<>(ALL_TEAM_IDS_SIZE);
        selectTeamIds = new ArrayList<>(SELECT_TEAM_IDS_SIZE);
        for (int i = 0; i < 10; i++) {
            String teamId = "test-team-id" + i;
            RankingDAOModel ranking = RankingDAOModel.builder()
                    .teamId(teamId)
                    .teamSlug("test-team-code-" + i)
                    .teamName("test team name " + i)
                    .ranking(i + 1)
                    .build();
            allRankingModels.add(ranking);
            allTeamIds.add(teamId);
            if (i < SELECT_TEAM_IDS_SIZE) {
                selectTeamIds.add(teamId);
            }
        }
    }

    @BeforeEach
    private void setup() {
        lenient().when(tournamentRepository.getTeamsInTournamentStage(anyString(), isNull()))
            .thenReturn(allTeamIds);
        lenient().when(tournamentRepository.getTeamsInTournamentStage(anyString(), notNull()))
            .thenReturn(selectTeamIds);
        lenient().when(rankingRepository.getTeamRankingsBatch(anyList()))
            .thenAnswer(i -> allRankingModels.subList(0, min(ALL_RANKINGS_SIZE, i.getArgument(0, List.class).size())));
        lenient().when(rankingRepository.getTopNRankedTeams(anyInt()))
            .thenAnswer(i -> allRankingModels.subList(0, min(ALL_RANKINGS_SIZE, i.getArgument(0))));
        lenient().when(teamEloRepository.getTeamElos(anyList()))
            .thenAnswer(i -> {
                List<String> teamIds = i.getArgument(0);
                ArrayList<TeamELODAOModel> teamEloDaos = new ArrayList<>(teamIds.size());
                for (String teamId: teamIds) {
                    teamEloDaos.add(TeamELODAOModel.builder()
                            .teamId(teamId)
                            .elo(1000.0)
                            .build()
                    );
                }
                return teamEloDaos;
            });
    }

    @Test
    public void testSuccessfullyGetsTournamentRankings() {
        List<Ranking> results = rankingService.getTournamentRanking("test-tournament-id", "test-stage");
        // only gets the number of teams requested, which is the select set
        assertEquals(results.size(), SELECT_TEAM_IDS_SIZE);
        assertRankingRangeInResults(results, 1, SELECT_TEAM_IDS_SIZE);
    }

    @Test
    public void testSuccessfullyGetsTournamentRankingsWithoutStage() {
        List<Ranking> results = rankingService.getTournamentRanking("test-tournament-id", null);
        assertEquals(results.size(), ALL_TEAM_IDS_SIZE);
        assertRankingRangeInResults(results, 1, ALL_TEAM_IDS_SIZE);
    }

    @Test
    public void testSuccessfullyGetsGlobalRankings() {
        int numTeams = 5;
        List<Ranking> results = rankingService.getGlobalRanking(numTeams);
        assertEquals(results.size(), numTeams);
        assertRankingRangeInResults(results, 1, numTeams);
    }

    @Test
    public void testSuccessfullyGetsTeamRankings() {
        String[] teamIds = new String[]{"test-team-1", "test-team-2", "test-team-3"};
        List<Ranking> results = rankingService.getTeamRanking(teamIds);
        assertEquals(teamIds.length, results.size());
    }

    public void assertRankingRangeInResults(List<Ranking> results, int low, int high) {
        boolean highFound = false;
        for (int i = 0; i < results.size(); i++) {
            assertEquals(low + i, results.get(i).getRank());
            if (results.get(i).getRank() == high) {
                highFound = true;
            }
        }
        assertTrue(highFound);
    }

}
