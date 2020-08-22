package com.player.es.Domain.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashMap;

@Data
/*  管理-赛事-item-pojo*/
public class MagMatchPojo {
    String season;
    String matchId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm",timezone = "GMT+8")
    Date date;
    LinkedHashMap<String,String> home;
    LinkedHashMap<String,String> away;
    Integer homeScore;
    Integer awayScore;
    Integer status;
    public MagMatchPojo(Object season, Object matchId, Object date, Object homeId, Object homeName,
                        Object awayId, Object awayName, Object homeScore, Object awayScore, Object status){
        this.season = season.toString();
        this.matchId = matchId.toString();
        this.date = (Date)date;
        this.home  = new LinkedHashMap<>();
        home.put("teamId",homeId.toString());
        home.put("name",homeName.toString());
        this.away = new LinkedHashMap<>();
        away.put("teamId",awayId.toString());
        away.put("name",awayName.toString());
        this.homeScore = Integer.valueOf(homeScore.toString());
        this.awayScore = Integer.valueOf(awayScore.toString());
        this.status = Integer.valueOf(status.toString());
    }
}
