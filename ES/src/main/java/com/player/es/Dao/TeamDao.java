package com.player.es.Dao;

import com.player.es.Domain.POJO.MatchPojo;
import com.player.es.Domain.TeamDomain;
import org.apache.shiro.crypto.hash.Hash;

import java.util.*;

public interface TeamDao {
    List<TeamDomain> test();
    List<Map> getTeamList();
    TeamDomain getTeamInfo(String teamId);
    int getTeamCount();
    // 某队-所有球员-某赛季-场均数据-得分-助攻-抢断-盖帽-篮板
    List<HashMap> getPlayerAvgData(String teamId, String season);
    //某队-赛季-所有赛事
    ArrayList<MatchPojo> getTeamSeasonMatchList(String teamId, String season);
}
