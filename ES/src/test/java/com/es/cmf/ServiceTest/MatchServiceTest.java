package com.es.cmf.ServiceTest;

import com.player.es.cmf.Service.MatchDataService;
import org.junit.jupiter.api.Test;
import com.player.es.cmf.Service.MatchService;

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
    @Test
    public void testGetMaxItemOfTeam(){
        MatchDataService test = new MatchDataService();
        System.out.println(test.getMaxItemOfTeam("2019-2020"));
    }
    @Test
    public void test(){
        MatchDataService test = new MatchDataService();
        System.out.println(test.getBarData("cba2020015","2019-2020").toString());
    }
}
