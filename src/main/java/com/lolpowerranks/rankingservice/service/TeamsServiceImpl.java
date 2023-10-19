package com.lolpowerranks.rankingservice.service;

import com.lolpowerranks.rankingservice.model.Team;
import com.lolpowerranks.rankingservice.model.dao.TeamDAOModel;
import com.lolpowerranks.rankingservice.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TeamsServiceImpl implements TeamsService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> getTeams() {
        List<TeamDAOModel> teamDaos = teamRepository.getAllTeams();
        ArrayList<Team> teams = new ArrayList<>(teamDaos.size());
        teamDaos.forEach(teamDao -> {
            Team team = teamDao.toTeam();
            teams.add(team);
        });
        Collections.sort(teams);
        return teams;
    }
}
