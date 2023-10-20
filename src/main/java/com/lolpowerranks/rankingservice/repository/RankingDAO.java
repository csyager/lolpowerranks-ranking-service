package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.RankingDAOModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("RankingDAO")
@Slf4j
public class RankingDAO implements RankingRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public static final String TABLE_NAME = "rankings";

    public List<RankingDAOModel> getTopNRankedTeams(int topN) {
        Map<String, AttributeValue> expressionAttrVal = new HashMap<>();
        expressionAttrVal.put(":rankAttribute", new AttributeValue().withN(String.valueOf(topN)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("ranking <= :rankAttribute").withExpressionAttributeValues(expressionAttrVal);
        return mapper.scan(RankingDAOModel.class, scanExpression);
    }

    public List<RankingDAOModel> getTeamRankingsBatch(List<String> teamIds) {
        List<KeyPair> keyPairList = teamIds.stream()
                .map(teamId -> new KeyPair().withHashKey(teamId))
                .toList();

        Map<Class<?>, List<KeyPair>> keyPairForTable = new HashMap<>();
        keyPairForTable.put(RankingDAOModel.class, keyPairList);

        Map<String, List<Object>> batchResults = mapper.batchLoad(keyPairForTable);
        return batchResults.getOrDefault(TABLE_NAME, Collections.emptyList())
                .stream()
                .map(element -> (RankingDAOModel) element)
                .toList();
    }
}
