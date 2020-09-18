package com.player.es.lss.Service;

import cn.hutool.db.ds.simple.SimpleDataSource;
import com.player.es.Config.MybatisConfig;
import com.player.es.lss.Dao.UserDao;
import com.player.es.Domain.UserDomain;
import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class UserService {
    public LinkedHashMap<String,Object> getUserInformation(String userId){
        try(SqlSession sqlSession=MybatisConfig.getSqlSession()){
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            return userDao.getUserInformation(userId);
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

    public boolean passwordChange(String userId, String password, String newPassword) {
        SqlSession sqlSession = MybatisConfig.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        UserDomain userDomain = userDao.getByUserID(userId);
        if (password.equals(userDomain.getPasswd())) {
            HashMap<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("password", newPassword);
            int changeNum = userDao.updatePassword(map);
            if (changeNum > 0) {
                sqlSession.commit();
                sqlSession.close();
                return true;
            } else {
                sqlSession.close();
                return false;
            }
        } else {
            sqlSession.close();
            return false;
        }
    }

    public boolean emailChange(String userId, String password, String newEmail){
        SqlSession sqlSession = MybatisConfig.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("userId", userId);
        hashMap.put("password", password );
        hashMap.put("newEmail", newEmail);
        int changeNum = mapper.updateEmail(hashMap);
        if (changeNum > 0 ){
            sqlSession.commit();
            sqlSession.close();
            return true;
        }
        else{
            sqlSession.close();
            return false;
        }
    }

//    用户注册
    public LinkedHashMap<String,Object> userCheckIn(HashMap<String,Object> hashMap){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()){
            UserDao userDao = sqlSession.getMapper(UserDao.class);
//            使用当前时间+ur生成userId
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssS");
            String userId = "ur"+simpleDateFormat.format(new Date());
            hashMap.put("userId",userId);
            boolean status = userDao.register(hashMap);
            if(status){
                return userDao.getUserInformation(userId);
            }
            else{
                return null;
            }
        }
    }
}
