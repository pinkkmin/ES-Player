package com.es.cmf.DaoTest;

import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import com.player.es.cmf.Dao.TeamDao;

import java.util.HashMap;
import java.util.List;

public class TeamDaoTest {
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
