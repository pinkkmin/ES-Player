package com.player.es.Service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;

public class MatchServiceTest {
    @Test
    void getTeamAvgOfSeason(){
        MatchService test  = new MatchService();
        test.getTeamAvgOfSeason("cba2020001");
    }
    @Test
    void getMatchInfo(){
        MatchService test  = new MatchService();
        System.out.println( test.getMatchInfo("100025842"));
    }
    @Test
    void getSeasonAllMatch(){
        MatchService test  = new MatchService();
        test.getSeasonAllMatch("2019-2020",0,100);
    }
}
