package com.player.es.Dao;

import com.player.es.Domain.TeamDomain;

import java.util.List;
import java.util.Map;

public interface TeamDao {
    List<TeamDomain> test();
    List<Map> getTeamList();
    TeamDomain getTeamInfo(String teamId);
    int getTeamCount();
}
