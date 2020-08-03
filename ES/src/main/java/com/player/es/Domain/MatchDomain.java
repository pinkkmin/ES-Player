/*
*  @auth pinkkmin
*  @info 赛事信息表对应实体类
*  @Date 2020/08/02
* */
package com.player.es.Domain;

import lombok.Data;
import java.util.Date;

@Data
public class MatchDomain {
    String matchId;     // 赛事ID
    String matchHome;   // 主队ID
    String matchAway;   // 客队ID
    String matchSeason; // 赛季 like：2019-2020
    Date matchDate;     // 赛事日期 like 2020-8-2 14:00
    int matchStatus;    // 比赛状态 已结束/未进行
    int homeScore;      // 主队的得分
    int awayScore;      // 客队得分
}
