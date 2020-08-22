package com.player.es.cmf.Domain.POJO;

import lombok.Data;

@Data
public class TeamComparePojo {
    Integer homeValue;
    String index;
    Integer awayValue;
    public TeamComparePojo(){}
    public  TeamComparePojo(Object home, String index, Object away ) {
       this.homeValue = Integer.valueOf(home.toString());
       this.index = index;
       this.awayValue = Integer.valueOf(away.toString());
    }

}
