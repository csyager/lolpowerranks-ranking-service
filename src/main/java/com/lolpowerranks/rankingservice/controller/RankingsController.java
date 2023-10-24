package com.lolpowerranks.rankingservice.controller;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.model.response.ExceptionResponse;
import com.lolpowerranks.rankingservice.model.response.GlobalRankingResponse;
import com.lolpowerranks.rankingservice.model.response.TeamRankingResponse;
import com.lolpowerranks.rankingservice.model.response.TournamentRankingResponse;
import com.lolpowerranks.rankingservice.service.RankingsService;
import com.lolpowerranks.rankingservice.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@Validated
public class RankingsController {

    @Autowired
    private final RankingsService service;

    public RankingsController(final RankingsService service) {
        this.service = service;
    }

    @Operation(summary = "Health check", description = "Health check")
    @ApiResponse(
            responseCode="200",
            description="OK",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Operation(summary = "Get rankings of teams in a tournament", description = "Get rankings of teams in a tournament.")
    @ApiResponse(
            responseCode="200",
            description="Successfully got rankings.",
            content = @Content(schema = @Schema(implementation = TournamentRankingResponse.class))
    )
    @GetMapping("/tournament_rankings/{tournamentId}")
    public ResponseEntity<TournamentRankingResponse> getTournamentRankings(
            @PathVariable("tournamentId") String tournamentId,
            @RequestParam(value = "stage", required = false) String stage
    ) {
        List<Ranking> rankings = service.getTournamentRanking(tournamentId, stage);
        TournamentRankingResponse response = TournamentRankingResponse.builder()
                .rankings(rankings)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get global rankings", description = "Get rankings of all teams globally.")
    @ApiResponse(
            responseCode="200",
            description="Successfully got rankings.",
            content = @Content(schema = @Schema(implementation = GlobalRankingResponse.class))
    )
    @ApiResponse(
            responseCode="400",
            description="Bad request",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
    @GetMapping("/global_rankings")
    public ResponseEntity<GlobalRankingResponse> getGlobalRankings(
            @Min(value=Constants.MIN_GLOBAL_RANKINGS,
                    message=Constants.LESS_THAN_MIN_GLOBAL_RANKINGS_MSG)
            @Max(value=Constants.MAX_GLOBAL_RANKINGS,
                    message=Constants.EXCEEDS_MAX_GLOBAL_RANKINGS_MSG)
            @RequestParam(value = "number_of_teams", defaultValue = Constants.DEFAULT_GLOBAL_RANKINGS) int numberOfTeams
    ) {
        List<Ranking> rankings = service.getGlobalRanking(numberOfTeams);
        GlobalRankingResponse response = GlobalRankingResponse.builder()
                .rankings(rankings)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get rankings for selected teams", description = "Get rankings for selected teams.")
    @ApiResponse(
            responseCode="200",
            description="Successfully got rankings.",
            content = @Content(schema = @Schema(implementation = TeamRankingResponse.class))
    )
    @ApiResponse(
            responseCode="400",
            description="Bad request",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
    )
    @GetMapping("/team_rankings")
    public ResponseEntity<TeamRankingResponse> getTeamRankings(
            @RequestParam(value="team_ids", required=false) @NotEmpty(message = Constants.EMPTY_TEAM_IDS_MSG) String[] teamIds
    ) {
        List<Ranking> rankings = service.getTeamRanking(teamIds);
        TeamRankingResponse response = TeamRankingResponse.builder()
                .rankings(rankings)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
