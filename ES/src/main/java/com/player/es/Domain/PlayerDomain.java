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
    int logo;
    public  PlayerDomain() {
    }
    public PlayerDomain(String id, String teamId_,String name) {
        this.playerId = id;
        this.teamId = teamId_;
        this.playerNumber = 0;
        this.playerBirth  = new Date();
        this.playerName = name;
        this.playerWeight = "0";
        this.playerHeight = "0";
        this.playerPosition = "C";
        this.playerServicing = 1;
        this.logo = logo;
        this.playerWingspan = "0";
    }
    public  PlayerDomain(String id, String teamId_,String name, int num, Date birth, String height, String weight, String position, int logo) {
        this.playerId = id;
        this.teamId = teamId_;
        this.playerNumber = num;
        this.playerBirth  = birth;
        this.playerName = name;
        this.playerWeight = weight;
        this.playerHeight = height;
        this.playerPosition = position;
        this.playerServicing = 1;
        this.logo = logo;
        this.playerWingspan = "0";
    }
}
