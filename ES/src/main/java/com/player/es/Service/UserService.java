package com.player.es.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.UserDao;
import com.player.es.Domain.UserDomain;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    public UserDomain getById(String userID) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<UserDomain> test = userDao.select();
            return test.get(0);
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

    public List<UserDomain> test() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            return userDao.select();
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
}
