package com.player.es.cmf.Domain.POJO;

import lombok.Data;

import java.text.DecimalFormat;

@Data
//饼图-项
public class PieItemPojo {
    String name;
    Double  value;
    public PieItemPojo() {

    }
    public PieItemPojo(String name , Double value) {
        this.name = name;
        this.value = value;
    }
    public PieItemPojo(String name , Object  value) {
        this.name = name;
        DecimalFormat df = new DecimalFormat("#.0");
        this.value = Double.valueOf(df.format(value.toString()));
    }
}
