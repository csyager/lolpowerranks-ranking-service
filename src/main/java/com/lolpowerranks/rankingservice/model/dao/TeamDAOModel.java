package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lolpowerranks.rankingservice.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = "teams")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDAOModel {
    @DynamoDBHashKey(attributeName = "team_id")
    private String teamId;
    @DynamoDBAttribute(attributeName = "team_acronym")
    private String teamAcronym;
    @DynamoDBAttribute(attributeName = "team_name")
    private String teamName;
    @DynamoDBAttribute(attributeName = "team_slug")
    private String teamSlug;

    public Team toTeam() {
        return Team.builder()
                .teamId(teamId)
                .teamAcronym(teamAcronym)
                .teamName(teamName)
                .teamSlug(teamSlug)
                .build();
    }
}
