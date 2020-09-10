package com.player.es.lss.Dao;

import com.player.es.Domain.NoticeDomain;
import com.player.es.Domain.PlayerDomain;
import com.player.es.Domain.TeamDomain;
import com.player.es.Domain.UserDomain;

import java.util.LinkedHashMap;
import java.util.List;

public interface GlobalDao {
    //    获取指定数量的用户信息
    List<UserDomain> getActualNumUser(int num);

    //    获取所有球队信息
    List<TeamDomain> getAllTeam();

    //    根据球队id获取球队球员信息
    List<PlayerDomain> getTeamPlayer(String teamId);

    //    获取指定赛事的主客队球员信息
    List<PlayerDomain> getMatchPlayer(String matchId);

    //    获取所有球员信息-按球队分组
    List<LinkedHashMap<String, Object>> getAllTeamId();

    List<LinkedHashMap<String, Object>> getTeamPlayerId(String teamId);

    //    获取指定数量球员信息
    List<PlayerDomain> getActualNumPlayer(int num);

    //    获取所有指定数量公告信息???
    List<NoticeDomain> getActualNumNotice(int num);

    //  根据球队id获取球队名
    String getActualTeamName(String teamId);

    //  根据用户id获取用户名
    String getActualUserName(String userId);

    //  根据队员id获取playerName
    String getActualPlayerName(String playerId);
}