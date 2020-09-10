package com.player.es.lss.Domain.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashMap;

@Data
public class NoticePojo {
    String noticeId;
    String authId;
    String auth;
    String authName;
    String title;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm",timezone = "GMT+8")
    Date date;
    String content;
    LinkedHashMap<String, String> home;
    LinkedHashMap<String, String> away;
    LinkedHashMap<String, String> player;


    public NoticePojo(String noticeId, String authId, String authName, String title, Object date, String content,
                      String homeId, String homeName, String awayId, String awayName, String playerId, String playerName){
        this.noticeId = noticeId;
        this.authId = authId;
        this.authName =authName;
        this.title = title;
        this.date = (Date)date;
        this.content = content;
        this.home = new LinkedHashMap<>();
        this.away = new LinkedHashMap<>();
        this.player = new LinkedHashMap<>();
        this.home.put("homeId", homeId);
        this.home.put("homeName", homeName);
        this.away.put("awayId", awayId);
        this.away.put("awayName", awayName);
        this.player.put("playerId", playerId);
        this.player.put("playerName", playerName);
    }
}
