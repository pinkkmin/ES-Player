package com.player.es.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.MatchDao;
import com.player.es.Dao.UserDao;
import com.player.es.Domain.UserDomain;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class MatchService {
    public Map getTeamSort(String teamId, String season) {
        List<Map> team = getALLTeamSort(season);
        for (Map item : team
        ) {
            if (teamId.equals(item.get("teamId"))) return item;
        }
        return null;
    }
    public List<Map> getALLTeamSort(String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            List<Map> demo = matchDao.getAllTeamSort(season);
            return demo ;
        }
    }
}
