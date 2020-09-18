package com.player.es.lss;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String [] args){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssS");
        String userId = "ur"+simpleDateFormat.format(new Date());
        System.out.println(userId);
    }
}
