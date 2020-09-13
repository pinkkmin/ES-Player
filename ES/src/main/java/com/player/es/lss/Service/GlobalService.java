package com.player.es.lss.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Domain.NoticeDomain;
import com.player.es.Domain.PlayerDomain;
import com.player.es.Domain.TeamDomain;
import com.player.es.lss.Dao.GlobalDao;
import com.player.es.lss.Dao.UserDao;
import com.player.es.lss.Domain.POJO.NoticePojo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

import java.util.*;

@Service
public class GlobalService {
//    获取指定数量用户信息
    public LinkedHashMap<String, Object> getActualNumUser(int num){
        try(SqlSession sqlSession= MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
            hashMap.put("count", num);
            hashMap.put("data", globalDao.getActualNumUser(num));
            return hashMap;
        }
    }

//    获取所有球队信息列表
    public LinkedHashMap<String, Object> getAllTeam(){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String , Object> linkedHashMap = new LinkedHashMap<>();
            List<TeamDomain> list = globalDao.getAllTeam();
            linkedHashMap.put("count", list.size());
            linkedHashMap.put("data", list);
            return linkedHashMap;
        }
    }

//    获取指定球队-球员信息
    public LinkedHashMap<String, Object> getTeamPlayer(String teamId){
        try( SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            List<PlayerDomain> list = globalDao.getTeamPlayer(teamId);
            linkedHashMap.put("count", list.size());
            linkedHashMap.put("data", list);
            return linkedHashMap;
        }
    }

//    获取指定赛事球员信息
    public List<PlayerDomain> getMatchPlayer(String matchId){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            return globalDao.getMatchPlayer(matchId);
        }
    }

//    获取所有球队球员信息-按球队分组
    public List<Object> getAllTeamPlayer(){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            List<LinkedHashMap<String, Object>> teamIds = globalDao.getAllTeamId();
            System.out.println(teamIds);
            List<Object> list = new ArrayList<>();
            for(LinkedHashMap<String, Object> linkedHashMap:teamIds) {
                List<LinkedHashMap<String, Object>> playerIds = globalDao.getTeamPlayerId(linkedHashMap.get("team_id").toString());
                linkedHashMap.put("data", playerIds);
                list.add(linkedHashMap);
            }
            return list;
        }
    }

//    获取指定数量球员信息
    public LinkedHashMap<String, Object> getAllPlayerList(int num){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            List<PlayerDomain> list = globalDao.getActualNumPlayer(num);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("count", num);
            linkedHashMap.put("data", list);
            return linkedHashMap;
        }
    }

//    获取所有球队-所有公告
    public LinkedHashMap<String, Object> getAllNotices(int num){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            List<NoticeDomain> noticeDomains = globalDao.getActualNumNotice(num);
            List<Object> noticePojos = new LinkedList<>();
//            System.out.println(noticeDomains);
            for(NoticeDomain noticeDomain:noticeDomains){
                NoticePojo noticePojo=new NoticePojo(noticeDomain.getNoticeId(),noticeDomain.getAuthId(),globalDao.getActualUserName(noticeDomain.getAuthId()),
                        noticeDomain.getTitle(),noticeDomain.getNoticeDate(),noticeDomain.getContent(),
                        noticeDomain.getHomeId(),globalDao.getActualTeamName(noticeDomain.getHomeId()),noticeDomain.getAwayId(),
                        globalDao.getActualTeamName(noticeDomain.getAwayId()),noticeDomain.getPlayerId(),globalDao.getActualPlayerName(noticeDomain.getPlayerId()));
                noticePojos.add(noticePojo);
            }
            linkedHashMap.put("count", num);
            linkedHashMap.put("data", noticePojos);
            return linkedHashMap;
        }
    }
}
