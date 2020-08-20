package com.player.es;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.MatchDao;
import com.player.es.Dao.PlayerDao;
import com.player.es.Service.MatchService;
import com.player.es.Utils.InitUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EsApplication {

    public static void main(String[] args) {
       SpringApplication.run(EsApplication.class, args);

    }

}
