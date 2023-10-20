package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.StageDAOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("StageDAO")
public class StageDAO implements StageRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public static final String TABLE_NAME = "tournament_stages";

    @Override
    public List<StageDAOModel> getTournamentStages(String tournamentId) {
        Map<String, AttributeValue> expressionAttrVal = new HashMap<>();
        expressionAttrVal.put(":partitionKey", new AttributeValue().withS(tournamentId));
        DynamoDBQueryExpression<StageDAOModel> queryExpression = new DynamoDBQueryExpression<StageDAOModel>()
                .withKeyConditionExpression("tournament_id = :partitionKey")
                .withExpressionAttributeValues(expressionAttrVal);
        return mapper.query(StageDAOModel.class, queryExpression);
    }
}
