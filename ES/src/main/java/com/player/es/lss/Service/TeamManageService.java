package com.player.es.lss.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.lss.Dao.TeamManageDao;
import net.sf.saxon.expr.Component;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamManageService {
//    球队管理：编辑球队
    public int editTeam(HashMap<String,String> hashMap){
        try(SqlSession sqlSession= MybatisConfig.getSqlSession()){
            TeamManageDao teamManageDao=sqlSession.getMapper(TeamManageDao.class);
            int status = teamManageDao.editTeam(hashMap);
            if(status>0){
                sqlSession.commit();
            }
            return status;
        }
    }

//    球队管理：编辑球员
    public int editPlayer(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession= MybatisConfig.getSqlSession()){
            TeamManageDao teamManageDao =sqlSession.getMapper(TeamManageDao.class);
            int status = teamManageDao.editPlayer(hashMap);
            if(status > 0 ){
                sqlSession.commit();
            }
            return status;
        }
    }

//    球队管理：查询球员
    public LinkedHashMap<String,Object> queryPlayer(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamManageDao teamManageDao = sqlSession.getMapper(TeamManageDao.class);
            hashMap.put("startNum", (int)hashMap.get("page")*(int)hashMap.get("pageSize"));
            LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<>();
            List<Object> list = teamManageDao.queryPlayer(hashMap);
            linkedHashMap.put("count", teamManageDao.queryPlayerNum(hashMap));
            linkedHashMap.put("data", list);
            return linkedHashMap;
        }
    }
}

