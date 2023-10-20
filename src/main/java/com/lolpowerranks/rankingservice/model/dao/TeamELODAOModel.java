package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lolpowerranks.rankingservice.repository.TeamELODAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = TeamELODAO.TABLE_NAME)
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamELODAOModel {
    @DynamoDBHashKey(attributeName = "team_id")
    private String teamId;
    @DynamoDBAttribute(attributeName = "elo")
    private Double elo;
}
