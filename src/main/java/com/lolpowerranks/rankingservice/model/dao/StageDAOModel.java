package com.lolpowerranks.rankingservice.model.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lolpowerranks.rankingservice.model.Stage;
import com.lolpowerranks.rankingservice.repository.StageDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = StageDAO.TABLE_NAME)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StageDAOModel {
    @DynamoDBHashKey(attributeName = "tournament_id")
    private String tournamentId;
    @DynamoDBRangeKey(attributeName = "stage_name")
    private String stageName;
    @DynamoDBAttribute(attributeName = "stage_slug")
    private String stageSlug;
    @DynamoDBAttribute(attributeName = "stage_type")
    private String stageType;

    public Stage toStage() {
        return Stage.builder()
                .tournamentId(tournamentId)
                .stageName(stageName)
                .stageSlug(stageSlug)
                .stageType(stageType)
                .build();
    }
}
