package com.player.es.cmf.Domain.POJO;

import lombok.Data;
import java.text.DecimalFormat;

@Data
public class TeamComparePojo {
    Double homeValue;
    String index;
    Double awayValue;
    public TeamComparePojo(){}
    public  TeamComparePojo(Object home, String index, Object away ) {

       this.homeValue = Double.valueOf(home.toString());
       this.index = index;
       this.awayValue = Double.valueOf(away.toString());
    }
    public  TeamComparePojo(Object home, String index, Object away ,int homeGame,int awayGame) {
        DecimalFormat  df = new DecimalFormat("###.0");
        this.homeValue = Double.valueOf(df.format(Double.valueOf(home.toString())/homeGame));
        this.index = index;
        this.awayValue = Double.valueOf(df.format(Double.valueOf(away.toString())/awayGame));
    }
}
