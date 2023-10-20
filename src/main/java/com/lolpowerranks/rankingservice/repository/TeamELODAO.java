package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lolpowerranks.rankingservice.model.dao.TeamELODAOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("TeamELODAO")
public class TeamELODAO implements TeamELORepository {
    @Autowired
    private DynamoDBMapper mapper;

    public static final String TABLE_NAME = "team_elos";

    @Override
    public List<TeamELODAOModel> getTeamElos(List<String> teamIds) {
        List<KeyPair> keyPairList = teamIds.stream()
                .map(teamId -> new KeyPair().withHashKey(teamId))
                .toList();

        Map<Class<?>, List<KeyPair>> keyPairForTable = new HashMap<>();
        keyPairForTable.put(TeamELODAOModel.class, keyPairList);

        Map<String, List<Object>> batchResults = mapper.batchLoad(keyPairForTable);
        return batchResults.getOrDefault(TABLE_NAME, Collections.emptyList())
                .stream()
                .map(element -> (TeamELODAOModel) element)
                .toList();
    }
}
