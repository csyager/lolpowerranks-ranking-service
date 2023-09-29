package com.lolpowerranks.rankingservice.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeamDAO {
    @Autowired
    private final AmazonDynamoDB client;

    private final DynamoDBMapper mapper;

    public TeamDAO(AmazonDynamoDB client) {
        this.client = client;
        this.mapper = new DynamoDBMapper(client);
    }


}
