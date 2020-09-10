package com.player.es.cmf.Domain.POJO;

import lombok.Data;

import java.util.Map;

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
    Integer utils(String value){
        Double value1 = Double.valueOf(value);
        return value1.intValue();
    }
    public MatchDataPojo(){}
    // Map name: key-value -> index-item like 3-score
    public MatchDataPojo(Map<Integer, String> name, String[] itemList) {
        this.playerId = "0";
        this.number = 0;
        for (int i = 0; i < itemList.length; i++) {
           switch (name.get(i)){
               case "姓名":
               case "name":
                   this.name = itemList[i];
                   break;
               case "号码":
               case "number":
                   this.number = utils(itemList[i]);
                   break;
               case "得分":
               case "score":
                   this.score = utils(itemList[i]);
                   break;
               case "助攻":
               case "assist":
                   this.assist = utils(itemList[i]);
                   break;
               case "篮板":
               case "bound":
                   this.bound = utils(itemList[i]);
                   break;
               case "时间":
               case "time":
                   this.time = utils(itemList[i]);
                   break;
               case "抢断":
               case "steal":
                   this.steal = utils(itemList[i]);
                   break;
               case "盖帽":
               case "block":
                   this.block = utils(itemList[i]);
                   break;
               case "失误":
               case "turnover":
                   this.turnOver = utils(itemList[i]);
                   break;
               case "罚球":
               case "free":
                   this.free = utils(itemList[i]);
                   break;
               case "犯规":
               case "foul":
                   this.foul = utils(itemList[i]);
                   break;
               default: break;
           }
        }
    }
}
