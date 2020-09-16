package com.player.es;

import com.player.es.Config.MybatisConfig;
import com.player.es.Utils.EmailUtils;
import com.player.es.Utils.FileUtils;
import com.player.es.Utils.InitUtils;
import com.player.es.cmf.Dao.TeamDao;
import com.player.es.cmf.Service.TeamService;
import com.player.es.lss.Dao.TeamManageDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EsApplication {

    public static void main(String[] args) {
      SpringApplication.run(EsApplication.class, args);
       /** init dataBase **/
//       InitUtils initUtils = new InitUtils();
//        initUtils.writeServiceR();
        //initUtils.initPlayerList();
//        initUtils.initMatchList("2018-2019");
//        initUtils.initMatchDataList("2014-2015");
//        initUtils.initMatchDataList();
//        initUtils.initMatchList("2014-2015");
//        initUtils.addPlayerList();
//        FileUtils fileUtils  = new FileUtils();
//        fileUtils.doMatchDataByXLS();


    }

}
