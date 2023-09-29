package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("RankingDAO")
@Slf4j
public class RankingDAO implements RankingRepository {
    @Autowired
    private final AmazonDynamoDB client;

    private final DynamoDBMapper mapper;

    public static final String TABLE_NAME = "rankings";

    public RankingDAO(AmazonDynamoDB client) {
        this.client = client;
        this.mapper = new DynamoDBMapper(client);
    }

    public List<RankingDAOModel> getTeamByRank(int rank) {
        Map<String, AttributeValue> expressionAttrVal = new HashMap<>();
        expressionAttrVal.put(":partitionKey", new AttributeValue().withN(String.valueOf(rank)));
        DynamoDBQueryExpression<RankingDAOModel> queryExpression = new DynamoDBQueryExpression<RankingDAOModel>()
                .withKeyConditionExpression("rank = :partitionKey").withExpressionAttributeValues(expressionAttrVal);
        return mapper.query(RankingDAOModel.class, queryExpression);
    }

    public List<RankingDAOModel> getTopNRankedTeams(int topN) {
        Map<String, AttributeValue> expressionAttrVal = new HashMap<>();
        expressionAttrVal.put(":rankAttribute", new AttributeValue().withN(String.valueOf(topN)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("ranking < :rankAttribute").withExpressionAttributeValues(expressionAttrVal);
        return mapper.scan(RankingDAOModel.class, scanExpression);
    }

    public List<RankingDAOModel> getTeamsBatch(List<String> teamIds) {
        List<KeyPair> keyPairList = teamIds.stream()
                .map(teamId -> new KeyPair().withHashKey(teamId))
                .toList();

        Map<Class<?>, List<KeyPair>> keyPairForTable = new HashMap<>();
        keyPairForTable.put(RankingDAOModel.class, keyPairList);

        Map<String, List<Object>> batchResults = mapper.batchLoad(keyPairForTable);
        return batchResults.getOrDefault("rankings", Collections.emptyList())
                .stream()
                .map(element -> (RankingDAOModel) element)
                .toList();
    }
}
