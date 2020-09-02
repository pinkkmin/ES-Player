package com.player.es.cmf.Dao;

import com.player.es.Domain.PlayerDomain;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface PlayerDao {
    List<PlayerDomain> test();
    //初始化球员列表
    public void initPlayerList(PlayerDomain pd);
    //初始化-追加外援
    public void initUpdatePlayer(PlayerDomain pd);
    //该球员是否存在
    public Boolean isExist(String playerId);
    //球员信息
    PlayerDomain getPlayerInfo(String playerId);
    // 球员信息
    PlayerDomain getPlayerInfoByName(String teamId, String playerName);
    //球员信息
    LinkedHashMap getPlayerInfoOfTeam(String playerId);
    //球员-赛季-各项均分
    LinkedHashMap<String,Double> getSeasonAvg(String playerId, String season);
    //所有球员-赛季-各项数场均
    LinkedHashMap<String,Double> getAllPlayerSeasonAvg(String season);
    //所有球员-赛季-各项数据最高
    LinkedHashMap<String,Double> getAllPlayerSeasonMax(String season);
    //球员-赛季得分情况
    ArrayList<LinkedHashMap<String,Double>> getPlayerSeasonData(@Param("playerId") String playerId, @Param("season")String season);
    //球员-赛季各项均分
    ArrayList<LinkedHashMap<String,Object>> getPlayerAvgDataOfSeason(@Param("playerId") String playerId);

}
