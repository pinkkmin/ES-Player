package com.player.es.Domain;

import lombok.Data;

import java.util.Date;

@Data
public class PlayerDomain {
    String playerId;    //球员ID
    String teamId;
    String playerName;
    int playerNumber;
    Date   playerBirth;
    String playerHeight;
    String playerWeight;
    String playerWingspan;
    String playerPosition;
    int playerServicing;
    public  PlayerDomain() {
    }
    public  PlayerDomain(String id, String teamId_,String name, Date birth, String height, String weight, String position, int servicing) {
        this.playerId = id;
        this.teamId = teamId_;
        this.playerBirth  = birth;
        this.playerName = name;
        this.playerWeight = weight;
        this.playerHeight = height;
        this.playerPosition = position;
        this.playerServicing = servicing;
        this.playerWingspan = "0";
    }
}
