package com.player.es.lss.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Utils.ResponseUnit;
import com.player.es.lss.Dao.UserDao;
import com.player.es.Domain.UserDomain;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserService {
    public LinkedHashMap<String,Object> getUserInformation(String userId){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            LinkedHashMap<String,Object> res  =userDao.getUserInformation(userId);
            int role = (int)res.get("role");
            ArrayList<String> roleList = new ArrayList<>();
            if(role == 1) {
              roleList.add("admin");
            }
            else if(role == 2) {
                roleList.add("root");
            }
            else roleList.add("user");
            res.put("roles",roleList);
            LinkedHashMap<String,Object> team = new LinkedHashMap<>();
            team.put("teamName",res.get("teamName"));
            team.put("teamId",res.get("teamId"));
            res.put("team",team);
            res.remove("teamId");
            res.remove("teamName");
            return res;
        }
    }

    public UserDomain getByUserID(String userID) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            return userDao.getByUserID(userID);
        }
    }

    public UserDomain getByUserName(String userName) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            return userDao.getByUserName(userName);
        }
    }

    public ResponseUnit altPasswd(String userId, String password, String newPassword) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            String passwd  = userDao.getPwdByUserID(userId);
            if (password.equals(passwd)){
                int status = userDao.altPasswd(userId,newPassword);
                if (status > 0) {
                    sqlSession.commit();
                    return ResponseUnit.succ("修改成功");
                }
                else{
                    return ResponseUnit.fail("修改失败");
                }
            } else {
                return ResponseUnit.fail("原密码不正确");
            }
        }
    }

    public ResponseUnit altUserInfo(Map <String,Object> map){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            String userName  = (String) map.get("userName");
            String userId = (String)map.get("userId");
            Map team = (Map)map.get("team");
            String teamId = (String)team.get("teamId");
            UserDomain  user =  userDao.getByUserName(userName);
            if(user != null ) {
                if(!user.getUserID().equals(userId)) //ID相同 说明同一用户未改名
                    return new ResponseUnit(400,"用户名已被占用,请重新输入","");
            }
            int status  = userDao.altUserName(userId,userName);
            if(status<0) {
                    return new ResponseUnit(400, "修改失败", "");
                }
            userDao.altTeam(userId,teamId);
            sqlSession.commit();
            return new ResponseUnit(200,"修改成功",getUserInformation(userId));
        }
    }

//    用户注册
    public LinkedHashMap<String,Object> userCheckIn(Map<String,String> hashMap){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            UserDao userDao = sqlSession.getMapper(UserDao.class);
//            使用当前时间+ur生成userId
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
            String userId = simpleDateFormat.format(new Date());
            hashMap.put("userId",userId);
            boolean status = userDao.register(hashMap);
            if(status){
                sqlSession.commit();
                return userDao.getUserInformation(userId);
            }
            else{
                return null;
            }
        }
    }
}
