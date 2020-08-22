package com.player.es.cmf.Dao;

import com.player.es.Domain.MatchDataDomain;

import java.util.*;

public interface MatchDataDao {
    List<MatchDataDomain> test();
    void  initMatchDataList(MatchDataDomain md);
    // 球队-某赛季-各项-总和
    LinkedHashMap<String,Object> getTeamSum(String teamId, String season);
    //球队-按赛季分组-各项数据总和
    ArrayList<LinkedHashMap<String,Object>> getTeamSumBySeason(String teamId);
    //某赛季-所有球队数据总和
    LinkedHashMap<String,Object> getAllTeamSum(String season);
    //赛事各项-总和
    List<LinkedHashMap<String,Object>> getSeasonSum(String season);
    // 球员各项-最高数据项
    LinkedHashMap<String,Object> getMaxItemOfPlayer(String season) ;
}
