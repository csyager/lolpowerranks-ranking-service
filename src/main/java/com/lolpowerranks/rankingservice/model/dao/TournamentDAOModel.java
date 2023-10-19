package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lolpowerranks.rankingservice.model.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = "tournaments")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDAOModel {
    @DynamoDBHashKey(attributeName = "tournament_id")
    private String tournamentId;
    @DynamoDBAttribute(attributeName = "tournament_name")
    private String tournamentName;

    public Tournament toTournament() {
        return Tournament.builder()
                .tournamentId(tournamentId)
                .tournamentName(tournamentName)
                .build();
    }
}
