package com.player.es.Dao;

import com.player.es.Domain.MatchDomain;

import java.util.List;
import java.util.Map;

public interface MatchDao {
    List<MatchDomain> test();
    //初始化match列表
    void initMatchList(MatchDomain md);
    // match是否存在
   Boolean isExist(String matchId);
   //获取全部球队的积分排名
   List<Map> getAllTeamSort(String season);
   //获取-一支球队的已结束比赛场次
    int getTeamGameCount(String teamId, String season);
   // 获取所有球队比赛场次
    List<Map> getGameCount(String season);
    //获取-本赛季-已结束场次
    int getMatchCount(String season);
}
