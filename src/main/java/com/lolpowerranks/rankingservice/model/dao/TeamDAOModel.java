package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "teams")
public class TeamDAOModel {
    @DynamoDBHashKey(attributeName = "team_id")
    private String teamId;
    @DynamoDBAttribute(attributeName = "team_acronym")
    private String teamAcronym;
    @DynamoDBAttribute(attributeName = "team_name")
    private String teamName;
    @DynamoDBAttribute(attributeName = "team_slug")
    private String teamSlug;
}
