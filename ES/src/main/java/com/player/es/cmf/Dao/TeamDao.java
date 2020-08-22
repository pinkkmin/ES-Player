package com.player.es.cmf.Dao;

import com.player.es.cmf.Domain.POJO.MatchPojo;
import com.player.es.Domain.TeamDomain;

import java.util.*;

public interface TeamDao {
    List<Map> getTeamList();
    TeamDomain getTeamInfo(String teamId);
    int getTeamCount();
    // 某队-所有球员-某赛季-场均数据-得分-助攻-抢断-盖帽-篮板
    List<HashMap> getPlayerAvgData(String teamId, String season);
    //某队-赛季-所有赛事
    ArrayList<MatchPojo> getTeamSeasonMatchList(String teamId, String season);
}
