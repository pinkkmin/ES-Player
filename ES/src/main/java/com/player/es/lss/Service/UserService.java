package com.player.es.lss.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.lss.Dao.UserDao;
import com.player.es.Domain.UserDomain;
import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {
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
}
