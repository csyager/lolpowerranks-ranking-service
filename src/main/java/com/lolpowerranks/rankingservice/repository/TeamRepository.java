package com.lolpowerranks.rankingservice.repository;

import com.lolpowerranks.rankingservice.model.dao.TeamDAOModel;

import java.util.List;

public interface TeamRepository {
    List<TeamDAOModel> getAllTeams();
}
