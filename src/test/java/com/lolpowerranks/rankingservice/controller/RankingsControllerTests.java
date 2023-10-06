package com.lolpowerranks.rankingservice.controller;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.service.RankingsService;
import com.lolpowerranks.rankingservice.util.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RankingsController.class)
public class RankingsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private static RankingsService service;

    private static final int ALL_STAGES_SIZE = 5;
    private static final int SELECT_STAGES_SIZE = 3;

    private static List<Ranking> allStageRankings;
    private static List<Ranking> selectStageRankings;

    @BeforeAll
    public static void init() {
        allStageRankings = new ArrayList<>(ALL_STAGES_SIZE);
        selectStageRankings = new ArrayList<>(SELECT_STAGES_SIZE);
        for (int i = 1; i <= ALL_STAGES_SIZE; i++) {
            Ranking ranking = Ranking.builder()
                    .teamId(String.valueOf(i))
                    .teamCode("test-team-" + i)
                    .teamName("TestTeam" + i)
                    .rank(i)
                    .build();
            if (i <= SELECT_STAGES_SIZE) {
                selectStageRankings.add(ranking);
            }
            allStageRankings.add(ranking);
        }
    }

    @BeforeEach
    public void setup() {
        when(service.getTournamentRanking(anyString(), isNull()))
            .thenReturn(allStageRankings);

        when(service.getTournamentRanking(anyString(), notNull()))
            .thenReturn(selectStageRankings);

        when(service.getGlobalRanking(anyInt()))
            .thenAnswer(i -> allStageRankings.subList(0, min(i.getArgument(0), allStageRankings.size())));

        when(service.getTeamRanking(any(String[].class)))
                .thenAnswer(i -> {
                    String[] teamIds = i.getArgument(0, String[].class);
                    return allStageRankings.subList(0, min(teamIds.length, allStageRankings.size()));
                });
    }

    @Test
    public void testSuccessfullyGetTournamentRankings() throws Exception {
        mockMvc.perform(get("/tournament_rankings/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(ALL_STAGES_SIZE)));
    }

    @Test
    public void testSuccessfullyGetTournamentRankingsWithStage() throws Exception {
        mockMvc.perform(get("/tournament_rankings/test?stage=test-stage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(SELECT_STAGES_SIZE)));
    }

    @Test
    public void testSuccessfullyGetGlobalRankings() throws Exception {
        mockMvc.perform(get("/global_rankings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(ALL_STAGES_SIZE)));
    }

    @Test
    public void testBadRequestWhenFewerThanMinGlobalRanking() throws Exception {
        mockMvc.perform(get("/global_rankings?number_of_teams=" + (Constants.MIN_GLOBAL_RANKINGS - 1)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.exceptionType").exists())
                .andExpect(jsonPath("$.exceptionType", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.message", equalTo(Constants.LESS_THAN_MIN_GLOBAL_RANKINGS_MSG)));
    }

    @Test
    public void testBadRequestWhenGreaterThanMaxGlobalRanking() throws Exception {
        mockMvc.perform(get("/global_rankings?number_of_teams=" + (Constants.MAX_GLOBAL_RANKINGS + 1)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.exceptionType").exists())
                .andExpect(jsonPath("$.exceptionType", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.message", equalTo(Constants.EXCEEDS_MAX_GLOBAL_RANKINGS_MSG)));
    }

    @Test
    public void testOnlyGetsRequestedNumberOfGlobalRankings() throws Exception {
        mockMvc.perform(get("/global_rankings?number_of_teams=1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testSuccesfullyGetsTeamRankings() throws Exception {
        mockMvc.perform(get("/team_rankings?team_ids=test-1,test-2,test-3"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testTeamRankingsUnsuccessfulWhenNoTeamsPassed() throws Exception {
        mockMvc.perform(get("/team_rankings"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.exceptionType").exists())
                .andExpect(jsonPath("$.exceptionType", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.message", equalTo(Constants.EMPTY_TEAM_IDS_MSG)));
    }

}
