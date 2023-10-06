package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.repository.RankingDAO;
import lombok.Builder;
import lombok.Setter;

@DynamoDBTable(tableName = RankingDAO.TABLE_NAME)
@Builder
@Setter
public class RankingDAOModel {
    private int ranking;
    private String teamId;
    private String teamName;
    private String teamSlug;


    @DynamoDBHashKey(attributeName = "team_id")
    public String getTeamId() {
        return teamId;
    }

    @DynamoDBAttribute(attributeName = "ranking")
    public int getRanking() {
        return ranking;
    }

    @DynamoDBAttribute(attributeName = "team_name")
    public String getTeamName() {
        return teamName;
    }

    @DynamoDBAttribute(attributeName = "team_slug")
    public String getTeamSlug() {
        return teamSlug;
    }

    public Ranking toRanking() {
        return Ranking.builder()
                .rank(ranking)
                .teamId(teamId)
                .teamCode(teamSlug)
                .teamName(teamName)
                .build();
    }
}
