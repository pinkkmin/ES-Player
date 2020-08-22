package com.player.es.Dao;

import com.player.es.Domain.MatchDomain;
import com.player.es.Domain.POJO.MatchDataPojo;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Array;
import java.util.*;

public interface MatchDao {
    List<MatchDomain> test();
    //初始化match列表
    void initMatchList(MatchDomain md);
    // match是否存在
   Boolean isExist(String matchId);
   //获取全部球队的积分排名
   ArrayList<LinkedHashMap> getAllTeamSort(String season);
   //获取-一支球队的已结束比赛场次
    int getTeamGameCount(String teamId, String season);
    // 球队-按赛季分组-赛季已结束的场次次数
    ArrayList<LinkedHashMap<String,Object>> getTeamGameCountBySeason(String teamId);
   // 获取所有球队比赛场次
    List<LinkedHashMap> getGameCount(String season);
    //获取-本赛季-已结束场次
    int getMatchCount(String season);
    /**某赛事的基本信息**/
    LinkedHashMap<String,Object> getMatchInfo(String matchId);
    // 某赛事-某项数据-最高者 Home Away
    LinkedHashMap<String,Object> getMaxScoreHomeOfMatch(String item,String matchId);
    LinkedHashMap<String,Object> getMaxBoundHomeOfMatch(String item,String matchId);
    LinkedHashMap<String,Object> getMaxAssistHomeOfMatch(String item,String matchId);
    LinkedHashMap<String,Object> getMaxScoreAwayOfMatch(String item,String matchId);
    LinkedHashMap<String,Object> getMaxBoundAwayOfMatch(String item,String matchId);
    LinkedHashMap<String,Object> getMaxAssistAwayOfMatch(String item,String matchId);
    // 某赛事-主客队-各项数据
    LinkedHashMap<String,Object> getTeamSumOfMatch(String isHome,String matchId);
    //某赛事-主客队-所有球员-所有数据
    ArrayList<MatchDataPojo> getTeamDataOfMatch(String matchId, String isHome);
    //本赛季-所有赛事
    ArrayList<LinkedHashMap<String,Object>> getSeasonAllMatch(@Param("season")String season, @Param("page")Integer page, @Param("pageSize")Integer pageSize);
}
