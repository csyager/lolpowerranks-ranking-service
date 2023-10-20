package com.lolpowerranks.rankingservice.repository;

import com.lolpowerranks.rankingservice.model.dao.TeamELODAOModel;

import java.util.List;

public interface TeamELORepository {
    List<TeamELODAOModel> getTeamElos(List<String> teamIds);
}
