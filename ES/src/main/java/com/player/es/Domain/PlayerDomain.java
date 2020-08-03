package com.player.es.Domain;

import lombok.Data;

import java.util.Date;

@Data
public class PlayerDomain {
    String playerId;    //球员ID
    String teamId;
    String playerName;
    Date   playerBirth;
    String playerHeight;
    String playerWeight;
    String playerWingspan;
    String playerPosition;
    int playerServicing;
}
