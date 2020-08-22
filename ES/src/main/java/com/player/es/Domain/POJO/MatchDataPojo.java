package com.player.es.Domain.POJO;

import lombok.Data;

@Data
public class MatchDataPojo {
    String playerId;
    String name;
    int number;
    int score;
    int bound;
    int assist;
    int time;
    int foul;
    int steal;
    int block;
    int turnOver;
    int free;
}
