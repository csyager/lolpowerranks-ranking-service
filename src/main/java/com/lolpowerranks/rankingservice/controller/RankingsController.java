package com.lolpowerranks.rankingservice.controller;

import com.lolpowerranks.rankingservice.model.Ranking;
import com.lolpowerranks.rankingservice.model.response.GlobalRankingResponse;
import com.lolpowerranks.rankingservice.model.response.TournamentRankingResponse;
import com.lolpowerranks.rankingservice.service.RankingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class RankingsController {

    @Autowired
    private final RankingsService service;

    public RankingsController(RankingsService service) {
        this.service = service;
    }

    @Operation(summary = "Get rankings of teams in a tournament", description = "Get rankings of teams in a tournament.")
    @ApiResponse(
            responseCode="200",
            description="Successfully got rankings.",
            content = @Content(schema = @Schema(implementation = TournamentRankingResponse.class))
    )
    @GetMapping("/tournament_rankings/{tournamentId}")
    public ResponseEntity<TournamentRankingResponse> getTournamentRankings(
            @NonNull @PathVariable("tournamentId") String tournamentId,
            @RequestParam("stage") Optional<String> stage
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
            content = @Content(schema = @Schema(implementation = TournamentRankingResponse.class))
    )
    @GetMapping("/global_rankings")
    public ResponseEntity<GlobalRankingResponse> getGlobalRankings(
            @RequestParam("number_of_teams") int numberOfTeams
    ) {
        List<Ranking> rankings = service.getGlobalRanking(numberOfTeams);
        GlobalRankingResponse response = GlobalRankingResponse.builder()
                .rankings(rankings)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
