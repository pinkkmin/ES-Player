package com.player.es.Controller;

import cn.hutool.core.map.MapUtil;
import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.*;
import com.player.es.Domain.*;
import com.player.es.Utils.ResponseUnit;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @GetMapping("/testTeam")
    public ResponseUnit test () {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao team = sqlSession.getMapper(TeamDao.class);
            List<TeamDomain> res = team.test();
            return ResponseUnit.succ(res);
        }
    }
    @GetMapping("/player")
    public ResponseUnit Player() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao team = sqlSession.getMapper(PlayerDao.class);
            List<PlayerDomain> res = team.test();
            return ResponseUnit.succ(res);
        }
    }
    @GetMapping("/match")
    public ResponseUnit match() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao team = sqlSession.getMapper(MatchDao.class);
            List<MatchDomain> res = team.test();
            return ResponseUnit.succ(res);
        }
    }
    @GetMapping("/service")
    public ResponseUnit service() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            ServiceDao team = sqlSession.getMapper(ServiceDao.class);
            List<ServiceDomain> res = team.test();
            return ResponseUnit.succ(res);
        }
    }
    @GetMapping("/matchdata")
    public ResponseUnit matchdata() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao team = sqlSession.getMapper(MatchDataDao.class);
            List<MatchDataDomain> res = team.test();
            return ResponseUnit.succ(res);
        }
    }
    @GetMapping("/notice")
    public ResponseUnit notice() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            NoticeDao team = sqlSession.getMapper(NoticeDao.class);
            List<NoticeDomain> res = team.test();
            return ResponseUnit.succ(res);
        }
    }
}
