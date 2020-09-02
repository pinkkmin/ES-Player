package com.player.es;

import com.player.es.Utils.InitUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EsApplication {

    public static void main(String[] args) {
       SpringApplication.run(EsApplication.class, args);
       /** init dataBase **/
//        InitUtils initUtils = new InitUtils();
        //initUtils.initPlayerList();
//        initUtils.initMatchList("2018-2019");
//        initUtils.initMatchDataList("2014-2015");
//        initUtils.initMatchDataList();
//        initUtils.initMatchList("2014-2015");
//        initUtils.addPlayerList();

    }

}
