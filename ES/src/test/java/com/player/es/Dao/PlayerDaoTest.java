package com.player.es.Dao;

import com.player.es.Config.MybatisConfig;
import com.player.es.Domain.PlayerDomain;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class PlayerDaoTest {
    @Test
    // pass test: playerDao.getPlayerInfo(String playerId)
    void testGetPlayerInfo() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            PlayerDomain demo = playerDao.getPlayerInfo("100002143");
            System.out.println(demo);
        }
    }
}
