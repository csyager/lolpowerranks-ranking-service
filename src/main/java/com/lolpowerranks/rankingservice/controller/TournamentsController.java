package com.lolpowerranks.rankingservice.controller;

import com.lolpowerranks.rankingservice.model.Tournament;
import com.lolpowerranks.rankingservice.model.response.TeamsResponse;
import com.lolpowerranks.rankingservice.model.response.TournamentsResponse;
import com.lolpowerranks.rankingservice.service.TournamentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tournaments")
@Validated
public class TournamentsController {

    @Autowired
    private final TournamentsService service;

    public TournamentsController(final TournamentsService service) {
        this.service = service;
    }

    @Operation(summary = "Get all tournaments", description = "Get all tournaments")
    @ApiResponse(
            responseCode="200",
            description="Successfully got tournaments.",
            content = @Content(schema = @Schema(implementation = TournamentsResponse.class))
    )
    @GetMapping()
    public ResponseEntity<TournamentsResponse> getTournaments() {
        List<Tournament> tournaments = service.getTournaments();
        TournamentsResponse response = TournamentsResponse.builder()
                .tournaments(tournaments)
                .numTournaments(tournaments.size())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
