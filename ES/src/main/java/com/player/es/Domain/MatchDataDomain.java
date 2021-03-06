package com.player.es.Domain;

import lombok.Data;

@Data
public class MatchDataDomain {
    String matchId;
    String playerId;
    int isHome;  //1home 0away
    int score;
    int bound;
    int assist;
    int matchTime;
    int block;
    int steal;
    int foul;
    int turnover;
    int free;
    public  MatchDataDomain(){

    }
    public  MatchDataDomain(String matchId, String playerId,int isHome, int score, int bound, int assist, int time,int block, int steal, int foul, int turnOver, int free){
        this.matchId = matchId;
        this.playerId = playerId;
        this.isHome = isHome;
        this.score = score;
        this.bound = bound;
        this.assist = assist;
        this.matchTime = time;
        this.block = block;
        this.steal = steal;
        this.foul = foul;
        this.turnover = turnOver;
        this.free = free;

    }
}
