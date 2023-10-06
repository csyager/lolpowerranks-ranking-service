package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lolpowerranks.rankingservice.repository.TournamentDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = TournamentDAO.TEAM_MAPPING_TABLE)
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentTeamMappingDAOModel {
    private String tournamentId;
    private String teamId;

    @DynamoDBHashKey(attributeName = "tournament_id")
    public String getTournamentId() {
        return tournamentId;
    }

    @DynamoDBRangeKey(attributeName = "team_id")
    public String getTeamId() {
        return teamId;
    }
}
