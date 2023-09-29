package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.TournamentTeamMappingDAOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TournamentDAO implements TournamentRepository {
    @Autowired
    private final AmazonDynamoDB client;

    private final DynamoDBMapper mapper;

    public static final String TEAM_MAPPING_TABLE = "tournament_teams";

    public TournamentDAO(AmazonDynamoDB client) {
        this.client = client;
        this.mapper = new DynamoDBMapper(client);
    }

    @Override
    public List<String> getTeamsInTournamentStage(String tournamentId, Optional<String> stage) {
        Map<String, AttributeValue> expressionAttrVal = new HashMap<>();
        expressionAttrVal.put(":partitionKey", new AttributeValue().withS(tournamentId));
        DynamoDBQueryExpression<TournamentTeamMappingDAOModel> queryExpression = new DynamoDBQueryExpression<TournamentTeamMappingDAOModel>()
                .withKeyConditionExpression("tournament_id = :partitionKey");
        // if the stage is passed as a param, filter results to only include that stage
        stage.ifPresent(s -> {
            expressionAttrVal.put(":stage_slug", new AttributeValue().withS(s));
            queryExpression.withFilterExpression("stage_slug = :stage_slug");
        });
        queryExpression.withExpressionAttributeValues(expressionAttrVal);
        List<TournamentTeamMappingDAOModel> tournamentTeamMappings = mapper.query(TournamentTeamMappingDAOModel.class, queryExpression);
        Set<String> teamIds = tournamentTeamMappings.stream()
                .map(TournamentTeamMappingDAOModel::getTeamId)
                .collect(Collectors.toSet());
        return teamIds.stream().toList();
    }
}
