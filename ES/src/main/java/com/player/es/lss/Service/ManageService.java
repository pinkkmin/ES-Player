package com.player.es.lss.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Domain.NoticeDomain;
import com.player.es.Domain.TeamDomain;
import com.player.es.Domain.UserDomain;
import com.player.es.lss.Dao.GlobalDao;
import com.player.es.lss.Dao.ManageDao;
import com.player.es.lss.Domain.DTO.UserDto;
import com.player.es.lss.Domain.POJO.NoticePojo;
import com.player.es.lss.Domain.POJO.UserPojo;
import com.sun.corba.se.impl.encoding.BufferManagerReadGrow;
import net.sf.saxon.expr.Component;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ManageService {
    //    后台管理-修改用户信息
    public UserPojo editUser(HashMap<String, Object> hashMap) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            ManageDao manageDao = sqlSession.getMapper(ManageDao.class);
            boolean status = manageDao.editUser(hashMap);
            if(status){
                sqlSession.commit();
                UserDomain userDomain = manageDao.getUserDomainByUserId(hashMap.get("userId").toString());
                UserPojo userPojo = new UserPojo(userDomain.getUserID(),userDomain.getUserName(),userDomain.getEmail(),userDomain.getRole(),userDomain.getTeamId(),manageDao.getTeamName(userDomain.getTeamId()));
                return userPojo;
            }
            else{
                return null;
            }
////            将object转化为map类型
//            @SuppressWarnings("unchecked")
//            Map<String, String> teamHash = (Map<String, String>) hashMap.get("team");

        }
    }
//    后台：查找用户信息
    public LinkedHashMap<String,Object> queryUser(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            ManageDao manageDao=sqlSession.getMapper(ManageDao.class);
            LinkedHashMap<String, Object> linkedHashMap=new LinkedHashMap<>();
            int startNum=(int)hashMap.get("pageSize")*(int)hashMap.get("page");
            //int endNum = startNum+(int)hashMap.get("pageSize");
            //cmf: limit限制条数 不是游标
            int endNum = (int)hashMap.get("pageSize");
            hashMap.remove("page");
            hashMap.remove("pageSize");
            hashMap.put("startNum",startNum);
            hashMap.put("endNum",endNum);

            for(String key:hashMap.keySet()){
                if(hashMap.get(key).toString().length()==0){
                    hashMap.put(key,null);
                }
            }
            List<UserDomain> userDomains=manageDao.queryUser(hashMap);
            linkedHashMap.put("count",manageDao.getQueryUserNum(hashMap));
            List<Object> list=new LinkedList<>();
            for(UserDomain userDomain:userDomains){
                UserPojo userPojo=new UserPojo(userDomain.getUserID(),userDomain.getUserName(),userDomain.getEmail(),userDomain.getRole(),userDomain.getTeamId(),manageDao.getTeamName(userDomain.getTeamId()));
                list.add(userPojo);
            }
            linkedHashMap.put("data",list);
            return linkedHashMap;
        }
    }
//    后台管理：新建球队
    public LinkedHashMap<String,Object> createTeam(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            ManageDao manageDao=sqlSession.getMapper(ManageDao.class);
            TeamDomain teamDomain=new TeamDomain();
            teamDomain.setTeamCity(hashMap.get("city").toString());
            teamDomain.setTeamClub(hashMap.get("club").toString());
            teamDomain.setTeamCoach(hashMap.get("coach").toString());
            teamDomain.setTeamHome(hashMap.get("home").toString());
            teamDomain.setTeamName(hashMap.get("name").toString());
            String currentId=manageDao.getLastTeamId();
            int count=0;
            for(int i=currentId.length()-3;i<currentId.length();i++){
                count=count*10+(currentId.charAt(i)-'0');
            }
            String teamId;
            count+=1;       //id增加1
            if(count>=100){
                teamId=currentId.substring(0,7)+String.valueOf(count);
            }
            else{
                teamId=currentId.substring(0,7)+"0"+String.valueOf(count);
            }
            System.out.println(teamId);
            teamDomain.setTeamId(teamId);
            teamDomain.setTeamStatus(0);
            int status=manageDao.addTeam(teamDomain);
            sqlSession.commit();
            TeamDomain teamDomain1=manageDao.getActualTeam(teamId);
            LinkedHashMap<String, Object> linkedHashMap=new LinkedHashMap<>();
            linkedHashMap.put("status", status);
            linkedHashMap.put("data",teamDomain1);
            return linkedHashMap;
        }
    }

//    后台管理：删除公告
    public int deleteNotice(String noticeId){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            ManageDao manageDao=sqlSession.getMapper(ManageDao.class);
            int status=manageDao.deleteActualNotice(noticeId);
            sqlSession.commit();
            return status;
        }
    }

//    后台管理：编辑公告
    public int editNotice(HashMap<String, Object> hashMap){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            ManageDao manageDao=sqlSession.getMapper(ManageDao.class);
            NoticeDomain noticeDomain=new NoticeDomain();
            noticeDomain.setNoticeId(hashMap.get("noticeId").toString());
            noticeDomain.setAuthId(hashMap.get("authId").toString());
            noticeDomain.setTitle(hashMap.get("title").toString());
            noticeDomain.setNoticeDate(new Date());
            noticeDomain.setContent(hashMap.get("content").toString());
            @SuppressWarnings("unchecked")
                    Map<String,String> player = (Map<String,String>)hashMap.get("player");
            @SuppressWarnings("unchecked")
                    Map<String,String> home = (Map<String,String>)hashMap.get("home");
            @SuppressWarnings("unchecked")
                    Map<String,String> away = (Map<String,String>)hashMap.get("away");
            noticeDomain.setPlayerId(player.get("playerId"));
            noticeDomain.setHomeId(home.get("homeId"));
            noticeDomain.setAwayId(away.get("awayId"));
            int status = manageDao.editNotice(noticeDomain);

            if(status>0){
                sqlSession.commit();
            }
            return status;
        }
    }

//    后台管理：查询公告
    public LinkedHashMap<String,Object> queryNotice(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            ManageDao manageDao=sqlSession.getMapper(ManageDao.class);
            GlobalDao globalDao=sqlSession.getMapper(GlobalDao.class);
            hashMap.put("startNum",(int)hashMap.get("page")*(int)hashMap.get("pageSize"));
            hashMap.put("endNum",(int)hashMap.get("pageSize"));
          ///  System.out.println(hashMap);
            List<NoticeDomain> noticeDomains=manageDao.queryNotice(hashMap);

            List<Object> list=new LinkedList<>();
            for(NoticeDomain noticeDomain:noticeDomains){
                NoticePojo noticePojo=new NoticePojo(noticeDomain.getNoticeId(),noticeDomain.getAuthId(),globalDao.getActualUserName(noticeDomain.getAuthId()),
                        noticeDomain.getTitle(),noticeDomain.getNoticeDate(),noticeDomain.getContent(),noticeDomain.getHomeId(),
                        globalDao.getActualTeamName(noticeDomain.getHomeId()),noticeDomain.getAwayId(),globalDao.getActualTeamName(noticeDomain.getAwayId()),noticeDomain.getPlayerId(),
                        globalDao.getActualPlayerName(noticeDomain.getPlayerId()));
                list.add(noticePojo);
            }
            LinkedHashMap<String,Object> linkedHashMap=new LinkedHashMap<>();
            linkedHashMap.put("count",manageDao.getQueryNoticeNum(hashMap));
            linkedHashMap.put("data",list);
           // System.out.println(linkedHashMap);
            return linkedHashMap;

        }
    }

//    后台管理：查询service记录，根据teamId
    public LinkedHashMap<String,Object> getServiceByTeamId(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            ManageDao manageDao = sqlSession.getMapper(ManageDao.class);
            hashMap.put("startNum",(int)hashMap.get("page")*(int)hashMap.get("pageSize"));
            hashMap.put("endNum",(int)hashMap.get("pageSize"));
            List<Object> list = manageDao.getServiceByTeamId(hashMap);
            LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("count",manageDao.getServiceByTeamIdNum(hashMap));
            linkedHashMap.put("data",manageDao.getServiceByTeamId(hashMap));
            return linkedHashMap;
        }
    }

//    后台管理：查询效力记录
    public LinkedHashMap<String,Object> queryService(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            ManageDao manageDao = sqlSession.getMapper(ManageDao.class);
            hashMap.put("startNum",(int)hashMap.get("page")*(int)hashMap.get("pageSize"));
            hashMap.put("endNum",(int)hashMap.get("pageSize"));
            List<Object> list =manageDao.queryService(hashMap);
            LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("count",manageDao.getQueryServiceNum(hashMap));
            linkedHashMap.put("data",manageDao.queryService(hashMap));
            return linkedHashMap;
        }
    }
}
