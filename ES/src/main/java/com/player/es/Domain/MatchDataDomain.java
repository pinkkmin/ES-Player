package com.player.es.Domain;

import lombok.Data;

@Data
public class MatchDataDomain {
    String matchId;
    String playerId;
    int isHome;
    int score;
    int bound;
    int assist;
    int time;
    int block;
    int steal;
    int foul;
    int turnOver;
    int free;
}
