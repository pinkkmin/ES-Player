package com.player.es.cmf.Dao;

import com.player.es.Domain.MatchDataDomain;
import com.player.es.cmf.Domain.Dto.MatchDataDto;
import org.apache.ibatis.annotations.Param;

import java.util.*;

public interface MatchDataDao {
    List<MatchDataDomain> test();
    int insertMatchData(Map<String,Object> data);
    void  initMatchDataList(MatchDataDomain md);
    // 球队-某赛季-各项-总和
    LinkedHashMap<String,Object> getTeamSum(String teamId, String season);
    // 球队-某赛季-各项-总和
    LinkedHashMap<String,Object> getTeamSumByCN(String teamId, String season);
    //球队-按赛季分组-各项数据总和
    ArrayList<LinkedHashMap<String,Object>> getTeamSumBySeason(String teamId);
    //某赛季-所有球队数据总和
    LinkedHashMap<String,Object> getAllTeamSum(String season);
    //赛事各项-总和
    List<LinkedHashMap<String,Object>> getSeasonSum(String season);
    // 球员各项-最高数据项
    LinkedHashMap<String,Double> getMaxItemOfPlayer(String season) ;
    //修改数据
    int editMatchData(MatchDataDto mdd);
    //查询球员
    MatchDataDomain queryMatchData(String matchId,String playerId);
    ///当修改数据时--->更新赛事得分总和
    int sumOfMatch(@Param("matchId") String matchId, @Param("isHome") int isHome);
    int updateMatchScore(@Param("matchId") String matchId,@Param("homeScore") int home, @Param("awayScore") int away);
}
