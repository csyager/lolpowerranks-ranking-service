package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.TournamentTeamMappingDAOModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
public class TournamentDAOTests {
    @Mock
    private DynamoDBMapper mapper;

    @InjectMocks
    private TournamentDAO tournamentDAO;

    private static List<TournamentTeamMappingDAOModel> tournamentTeams;

    private static final String TOURNAMENT_ID = "test-tournament-id";
    private static final int NUM_TEAMS = 10;
    // should be fewer than the total number of teams NUM_TEAMS
    private static final int NUM_TEAMS_WITH_STAGE = 5;

    @BeforeAll
    public static void init() {
        tournamentTeams = new ArrayList<>();
        for (int i = 0; i < NUM_TEAMS; i++) {
            tournamentTeams.add(TournamentTeamMappingDAOModel.builder()
                    .tournamentId(TOURNAMENT_ID)
                    .teamId("test-team-id-" + i)
                    .build());
        }
    }

    @BeforeEach
    public void setup() {
        lenient().when(mapper.query(any(), any()))
                .thenAnswer(i -> {
                    DynamoDBQueryExpression<TournamentTeamMappingDAOModel> queryExpression = i.getArgument(1);
                    Map<String, AttributeValue> attributeValues = queryExpression.getExpressionAttributeValues();
                    String tournamentId = attributeValues.get(":partitionKey").getS();
                    AttributeValue stageAttrVal = attributeValues.get(":stage_slug");
                    if (!tournamentId.equals(TOURNAMENT_ID)) {
                        return mock(PaginatedQueryList.class, withSettings().defaultAnswer(
                                new ForwardsInvocations(List.of())));
                    } else if (stageAttrVal != null) {
                        return mock(PaginatedQueryList.class, withSettings().defaultAnswer(
                                new ForwardsInvocations(tournamentTeams.subList(0, NUM_TEAMS_WITH_STAGE))));
                    } else {
                        return mock(PaginatedQueryList.class, withSettings().defaultAnswer(
                                new ForwardsInvocations(tournamentTeams)));
                    }

                });
    }

    @Test
    public void testSuccessfullyGetsTeamsInTournament() {
        List<String> results = tournamentDAO.getTeamsInTournamentStage(TOURNAMENT_ID, null);
        assertEquals(NUM_TEAMS, results.size());
    }

    @Test
    public void testSuccessfullyGetsTeamsInTournamentWithStage() {
        List<String> results = tournamentDAO.getTeamsInTournamentStage(TOURNAMENT_ID, "test-stage");
        assertEquals(NUM_TEAMS_WITH_STAGE, results.size());
    }

    @Test
    public void testGetsEmptyListWithWrongTournamentId() {
        List<String> results = tournamentDAO.getTeamsInTournamentStage("wrong-tournament-id", null);
        assertEquals(0, results.size());
    }
}
