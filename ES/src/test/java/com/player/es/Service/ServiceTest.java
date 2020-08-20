package com.player.es.Service;

import org.junit.jupiter.api.Test;

public class ServiceTest {
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
