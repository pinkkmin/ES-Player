package com.player.es.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.TeamDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class TeamService {
    public List<Map> getTeamList() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao team = sqlSession.getMapper(TeamDao.class);
            return team.getTeamList();
        }
    }
}
