package com.player.es.Dao;

import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
