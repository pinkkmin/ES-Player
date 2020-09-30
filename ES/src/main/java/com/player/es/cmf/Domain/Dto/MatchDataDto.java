package com.player.es.cmf.Domain.Dto;

import lombok.Data;

@Data
public class MatchDataDto {
    String matchId;
    String playerId;
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
