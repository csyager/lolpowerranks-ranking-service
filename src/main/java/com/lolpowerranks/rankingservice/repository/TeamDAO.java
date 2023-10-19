package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.lolpowerranks.rankingservice.model.dao.TeamDAOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TeamDAO")
public class TeamDAO implements TeamRepository {
    @Autowired
    private DynamoDBMapper mapper;

    public static final String TABLE_NAME = "teams";

    @Override
    public List<TeamDAOModel> getAllTeams() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(TeamDAOModel.class, scanExpression);
    }
}
