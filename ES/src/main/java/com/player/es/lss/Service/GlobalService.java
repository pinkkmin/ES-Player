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
    public LinkedHashMap<String, Object> getActualNumUser(int startNum,int endNum){
        try(SqlSession sqlSession= MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<>();
            hashMap.put("startNum", startNum);
            hashMap.put("endNum",endNum);
            LinkedHashMap<String,Object> hashMap1  = new LinkedHashMap<>();
            List<Object> list = new LinkedList<>();
            hashMap1.put("count",globalDao.getUserNum());
            List<UserDomain> userDomains = globalDao.getActualNumUser(hashMap);
            for(UserDomain userDomain:userDomains){
                LinkedHashMap<String,Object> linkedHashMap=new LinkedHashMap<>();
                linkedHashMap.put("userId",userDomain.getUserID());
                linkedHashMap.put("userName",userDomain.getUserName());
                linkedHashMap.put("email",userDomain.getEmail());
                linkedHashMap.put("type",userDomain.getRole());
                LinkedHashMap<String,String> team = new LinkedHashMap<>();
                team.put("teamId",userDomain.getTeamId());
                team.put("name",globalDao.getActualTeamName(team.get("teamId")));
                linkedHashMap.put("team",team);
                list.add(linkedHashMap);
            }
            hashMap1.put("data",list);
            return hashMap1;
        }
    }

//    获取所有球队信息列表
    public LinkedHashMap<String, Object> getAllTeam(){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String , Object> linkedHashMap = new LinkedHashMap<>();
            List<TeamDomain> list = globalDao.getAllTeam();
            linkedHashMap.put("count", list.size());
            List<Object> objects = new LinkedList<>();
            for(TeamDomain teamDomain:list){
                LinkedHashMap<String,String> linkedHashMap1 = new LinkedHashMap<>();
                linkedHashMap1.put("teamId",teamDomain.getTeamId());
                linkedHashMap1.put("name",teamDomain.getTeamName());
                linkedHashMap1.put("coach",teamDomain.getTeamCoach());
                linkedHashMap1.put("city",teamDomain.getTeamCity());
                linkedHashMap1.put("home",teamDomain.getTeamHome());
                linkedHashMap1.put("club",teamDomain.getTeamClub());
                objects.add(linkedHashMap1);
            }
            linkedHashMap.put("data", objects);
            return linkedHashMap;
        }
    }

//    获取指定球队-球员信息
    public LinkedHashMap<String, Object> getTeamPlayer(String teamId,int startNum,int endNum){
        try( SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            Map<String,Object> map = new HashMap<>();
            map.put("teamId",teamId);
            map.put("startNum",startNum);
            map.put("endNum",endNum);
            List<HashMap<String,Object>> list = globalDao.getTeamPlayer(map);
            linkedHashMap.put("count", globalDao.getTeamPlayerNum(map.get("teamId").toString()));
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
                List<LinkedHashMap<String, Object>> playerIds = globalDao.getTeamPlayerId(linkedHashMap.get("teamId").toString());
                linkedHashMap.put("data", playerIds);
                list.add(linkedHashMap);
            } 
            return list;
        }
    }

//    获取指定数量球员信息
    public LinkedHashMap<String, Object> queryPlayerList(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            List<Object> list = globalDao.queryPlayer(hashMap);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("count", globalDao.getPlayerNum());
            linkedHashMap.put("data", list);
            return linkedHashMap;
        }
    }

//    获取所有球队-所有公告
    public LinkedHashMap<String, Object> getAllNotices(int startNum,int endNum){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            Map<String,Integer> map = new HashMap<>();
            map.put("startNum",startNum);
            map.put("endNum",endNum);
            List<NoticeDomain> noticeDomains = globalDao.getActualNumNotice(map);
            List<Object> noticePojos = new LinkedList<>();
//            System.out.println(noticeDomains);
            for(NoticeDomain noticeDomain:noticeDomains){
                NoticePojo noticePojo=new NoticePojo(noticeDomain.getNoticeId(),noticeDomain.getAuthId(),globalDao.getActualUserName(noticeDomain.getAuthId()),
                        noticeDomain.getTitle(),noticeDomain.getNoticeDate(),noticeDomain.getContent(),
                        noticeDomain.getHomeId(),globalDao.getActualTeamName(noticeDomain.getHomeId()),noticeDomain.getAwayId(),
                        globalDao.getActualTeamName(noticeDomain.getAwayId()),noticeDomain.getPlayerId(),globalDao.getActualPlayerName(noticeDomain.getPlayerId()));
                noticePojos.add(noticePojo);
            }
            linkedHashMap.put("count", globalDao.getNoticeNum());
            linkedHashMap.put("data", noticePojos);
            return linkedHashMap;
        }
    }
}
