package com.player.es.Service;

import org.junit.jupiter.api.Test;

public class TeamServiceTest {
    @Test
    void testGetScoreOfAllPlayer(){
        TeamService team = new TeamService();
        team.getTeamSeasonMatchList("cba2020008","2019-2020");
    }
}
