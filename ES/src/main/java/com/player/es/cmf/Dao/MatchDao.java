package com.player.es.cmf.Dao;

import com.player.es.Domain.MatchDomain;
import com.player.es.cmf.Domain.Dto.MagMatchDto;
import com.player.es.cmf.Domain.Dto.QueryMatchDto;
import com.player.es.cmf.Domain.POJO.MatchDataPojo;
import org.apache.ibatis.annotations.Param;
import java.util.*;

public interface MatchDao {
    //获取-本赛季-名称
    LinkedHashMap<String,Object> getCurrSeason();
    //初始化match列表
    int insertMatch(Map<String,Object> map);
    void initMatchList(MatchDomain md);
    LinkedHashMap getTeamId(String matchId);
    ArrayList<String> getSeasonList();
    //root /编辑赛事 return row
    int editMatch(MagMatchDto item);
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
    LinkedHashMap<String,Double> getTeamSumOfMatch(String isHome,String matchId);
    //某赛事-主客队-所有球员-所有数据
    ArrayList<MatchDataPojo> getTeamDataOfMatch(String matchId, String isHome);
    //本赛季-所有赛事
    ArrayList<LinkedHashMap<String,Object>> getSeasonAllMatch(@Param("season")String season, @Param("page")Integer page, @Param("pageSize")Integer pageSize);
    // 查询赛事
    ArrayList<LinkedHashMap<String,Object>> queryMatch(QueryMatchDto item);
    int queryMatchCount(QueryMatchDto item);
    // 今天赛事
    ArrayList<LinkedHashMap<String,Object>> todayMatch();
    // 今天以后七场赛事
    ArrayList<LinkedHashMap<String,Object>> sevenMatchAfterToday();
    // 今日之前七场赛事
    ArrayList<LinkedHashMap<String,Object>> sevenMatchBeforeToday();
   //赛季列表
    //****
    //球队-按赛季分组-各项数据总和
    ArrayList<LinkedHashMap<String,Object>> getTeamSeasonSumList(String teamId);
    //所有球队-按赛季分组-各项数据总和
    ArrayList<LinkedHashMap<String,Object>> getAllTeamSeasonSumList();
    //球队-按赛季分组-比赛场次
    ArrayList<LinkedHashMap<String,Object>> getTeamGameCountList(String teamId);
    //球队-按赛季分组-比赛场次
    ArrayList<LinkedHashMap<String,Object>> getAllTeamGameCountList();
    //每月的比赛日
    ArrayList<String> getDayListByMonth(String season,String month);
    // 最近一个月的比赛
    ArrayList<String> getDayByLastMonth(String season);
    // 赛事-按天算
    ArrayList<LinkedHashMap<String,Object>> getMatchByDay(String season,String day);
    ///查询 为录入数据的比赛--且结束 场次
    int countNoDataMatch(Map<String,Object> map);
    ArrayList<LinkedHashMap<String,Object>> queryNoDataMatch(Map<String,Object> item);
    //获取球队的球员列表
    ArrayList<LinkedHashMap<String,Object>> getPlayerByTeam(String teamId);
}
