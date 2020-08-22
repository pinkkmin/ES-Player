package com.es.cmf.DaoTest;

import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import com.player.es.cmf.Dao.MatchDao;

import java.util.LinkedHashMap;
import java.util.List;

public class MatchDaoTest {
    @Test
    //测试 成功
    public void testGetALLTeamSort() {
        try (
                SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            List<LinkedHashMap> demo = matchDao.getAllTeamSort("2019-2020");
            for (LinkedHashMap item : demo
            ) {
                System.out.println(item);
            }
        }
    }

}
