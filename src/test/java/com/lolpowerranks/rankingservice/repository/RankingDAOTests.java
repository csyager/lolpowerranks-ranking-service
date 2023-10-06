package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.defaultanswers.ForwardsInvocations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
public class RankingDAOTests {

    @Mock
    private DynamoDBMapper mapper;

    @InjectMocks
    private RankingDAO rankingDAO;

    private static List<RankingDAOModel> topRankedTeams;

    @BeforeAll
    public static void init() {
        topRankedTeams = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            topRankedTeams.add(
                    RankingDAOModel.builder()
                            .ranking(i + 1)
                            .teamName("test-team-" + i)
                            .teamSlug("test-team-slug-" + i)
                            .teamId("test-team-id-" + i)
                            .build()
            );
        }
    }

    @BeforeEach
    public void setup() {
        lenient().when(mapper.batchLoad(anyMap()))
                .thenAnswer(i -> {
                    Map<Class<?>, List<KeyPair>> keyPairMap = i.getArgument(0);
                    List<KeyPair> keyPairList = keyPairMap.get(RankingDAOModel.class);
                    List<String> teamIds = new ArrayList<>();
                    for (KeyPair keyPair: keyPairList) {
                        teamIds.add(keyPair.getHashKey().toString());
                    }
                    List<RankingDAOModel> rankings = topRankedTeams.stream()
                            .filter(ranking -> teamIds.contains(ranking.getTeamId()))
                            .toList();
                    return Map.of("rankings", new ArrayList<>(rankings));
                });
    }

    @Test
    public void testSuccessfullyGetsTopNRankedTeams() {
        when(mapper.scan(any(), any()))
            .thenAnswer(i -> {
                DynamoDBScanExpression expression = i.getArgument(1);
                Map<String, AttributeValue> attributeValues = expression.getExpressionAttributeValues();
                int teamsRequested = Integer.parseInt(attributeValues.get(":rankAttribute").getN());
                return mock(PaginatedScanList.class, withSettings().defaultAnswer(
                        new ForwardsInvocations(topRankedTeams.subList(0, teamsRequested))));
            });

        int numTeams = 5;
        List<RankingDAOModel> result = rankingDAO.getTopNRankedTeams(numTeams);
        assertEquals(result.size(), numTeams);
        for (int i = 0; i < numTeams; i++) {
            assertEquals(result.get(i).getRanking(), i + 1);
        }
    }

    @Test
    public void successfullyGetsTeamRankings() {
        List<String> teamIds = Arrays.asList("test-team-id-1", "test-team-id-2", "test-team-id-3");
        List<RankingDAOModel> result = rankingDAO.getTeamRankingsBatch(teamIds);

        assertEquals(result.size(), teamIds.size());
        assertEquals("test-team-id-1", result.get(0).getTeamId());
        assertEquals(2, result.get(0).getRanking());
        assertEquals("test-team-id-2", result.get(1).getTeamId());
        assertEquals(3, result.get(1).getRanking());
        assertEquals("test-team-id-3", result.get(2).getTeamId());
        assertEquals(4, result.get(2).getRanking());

    }

    @Test
    public void successfullyGetsOneTeamRanking() {
        List<String> teamIds = List.of("test-team-id-5");
        List<RankingDAOModel> result = rankingDAO.getTeamRankingsBatch(teamIds);

        assertEquals(result.size(), 1);
        assertEquals("test-team-id-5", result.get(0).getTeamId());
        assertEquals(6, result.get(0).getRanking());
    }
}
