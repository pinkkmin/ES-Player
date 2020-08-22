package com.player.es.cmf.Dao;

import com.player.es.Domain.PlayerDomain;

import java.util.List;

public interface PlayerDao {
    List<PlayerDomain> test();
    //初始化球员列表
    public void initPlayerList(PlayerDomain pd);
    //该球员是否存在
    public Boolean isExist(String playerId);
    //球员信息
    PlayerDomain getPlayerInfo(String playerId);
    // 球员信息
    PlayerDomain getPlayerInfoByName(String teamId, String playerName);
}
