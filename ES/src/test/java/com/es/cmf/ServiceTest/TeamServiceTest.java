package com.es.cmf.ServiceTest;

import org.junit.jupiter.api.Test;
import com.player.es.cmf.Service.TeamService;

public class TeamServiceTest {
    @Test
    void testGetScoreOfAllPlayer(){
        TeamService team = new TeamService();
        team.getTeamSeasonMatchList("cba2020008","2019-2020");
    }
}
