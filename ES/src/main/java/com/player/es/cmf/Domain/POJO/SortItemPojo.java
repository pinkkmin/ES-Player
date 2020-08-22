package com.player.es.cmf.Domain.POJO;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
/*积分榜*/
public class SortItemPojo {
    Integer indexLeft;
    String teamLeft;
    String teamIdLeft;
    String wfLeft;
    Integer indexRight;
    String teamRight;
    String teamIdRight;
    String wfRight;
    public SortItemPojo() {}
    public SortItemPojo(LinkedHashMap lhs,LinkedHashMap rhs){
        indexLeft = Double.valueOf(lhs.get("rand").toString()).intValue();
        teamLeft = (String)lhs.get("teamName");
        teamIdLeft = (String)lhs.get("teamId");
        wfLeft = lhs.get("win").toString() + "/" + (String)lhs.get("fail").toString();
        indexRight = Double.valueOf(rhs.get("rand").toString()).intValue();
        teamRight = (String)rhs.get("teamName");
        teamIdRight = (String)rhs.get("teamId");
        wfRight = (String)rhs.get("win").toString() + "/" + (String)rhs.get("fail").toString();
    }

}
