package com.player.es.Dao;

import com.player.es.Domain.UserDomain;
import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    public void test() {
        //获取SqlSession
        try(SqlSession demo = MybatisConfig.getSqlSession()) {
            // 执行SQL语句
            UserDao userDao = demo.getMapper(UserDao.class);
            List<UserDomain> test = userDao.select();
            System.out.println("---------------------------testing-----------------------");
            for (UserDomain userDomain : test) {
                System.out.println(userDomain);
            }
            System.out.println("---------------------------testing-----------------------");

        }
    }
}
