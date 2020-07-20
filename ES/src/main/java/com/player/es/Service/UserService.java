package com.player.es.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.UserDao;
import com.player.es.Domain.UserDomain;
import com.player.es.Utils.ResponseUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public UserDomain getById(String userID) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<UserDomain> test = userDao.select();
            return test.get(0);
        }
    }
    public UserDomain getByUserID(String userID) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            return userDao.getByUserID(userID);
        }
    }
    public UserDomain getByUserName(String userName) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            return userDao.getByUserName(userName);
        }
    }
}
