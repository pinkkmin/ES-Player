package com.player.es.Dao;

import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamTest {
    @Test
    //pass
    //TeamDao List<Map> getPlayerAvgData(String teamId, String season)
    void getPlayerAvgData(){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            List<HashMap> demo  = teamDao.getPlayerAvgData("cba2020014","2019-2020");
            for (HashMap item : demo
                 ) {
                System.out.println(item);
            }
        }
    }
}
