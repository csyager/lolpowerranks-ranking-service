package com.lolpowerranks.rankingservice.controller;

import com.lolpowerranks.rankingservice.model.Team;
import com.lolpowerranks.rankingservice.model.response.TeamsResponse;
import com.lolpowerranks.rankingservice.service.TeamsService;
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
@RequestMapping("/teams")
@Validated
public class TeamsController {
    @Autowired
    private final TeamsService service;


    public TeamsController(final TeamsService service) {
        this.service = service;
    }

    @Operation(summary = "Get all teams", description = "Get all teams")
    @ApiResponse(
            responseCode="200",
            description="Successfully got teams.",
            content = @Content(schema = @Schema(implementation = TeamsResponse.class))
    )
    @GetMapping()
    public ResponseEntity<TeamsResponse> getTeams() {
        List<Team> teams = service.getTeams();
        TeamsResponse response = TeamsResponse.builder()
                .teams(teams)
                .numTeams(teams.size())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
