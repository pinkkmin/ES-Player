package com.player.es.Dao;

import com.player.es.Domain.MatchDataDomain;

import java.util.List;
import java.util.Map;

public interface MatchDataDao {
    List<MatchDataDomain> test();
    void  initMatchDataList(MatchDataDomain md);
    // 球队-各项-总和
    Map getTeamSum(String teamId, String season);
    Map getAllTeamSum(String season);
    //赛事各项-总和
    List<Map> getSeasonSum(String season);
    // 球员各项-最高数据项
    Map getMaxItemOfPlayer(String season) ;
}
