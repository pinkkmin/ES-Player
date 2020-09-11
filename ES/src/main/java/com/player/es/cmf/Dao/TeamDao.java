package com.player.es.cmf.Dao;

import com.player.es.cmf.Domain.POJO.MatchPojo;
import com.player.es.Domain.TeamDomain;

import java.util.*;

public interface TeamDao {
    // 获取-球队-列表
    List<Map> getTeamList();
    //赛季-列表 2019-2020 2018-2019
    ArrayList<LinkedHashMap> getSeasonList();
    TeamDomain getTeamInfo(String teamId);
    LinkedHashMap<String,Object> getTeamInfoById(String teamId);
    int getTeamCount();
    // 某队-所有球员-某赛季-场均数据-得分-助攻-抢断-盖帽-篮板
    List<HashMap> getPlayerAvgData(String teamId, String season);
    //某队-赛季-所有赛事
    ArrayList<MatchPojo> getTeamSeasonMatchList(String teamId, String season);
    // 球队-最近七场-比赛-得失分
    ArrayList<LinkedHashMap<String,Object>> lastSevenMatch(String teamId);
    //球队-过去一个赛季-比赛
    ArrayList<LinkedHashMap<String,Object>> lastSeasonMatch(String teamId,String season);
    ArrayList<LinkedHashMap<String,Object>> playerArrayByTeam(String teamId);
}
