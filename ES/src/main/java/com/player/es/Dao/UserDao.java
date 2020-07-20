package com.player.es.Dao;

import com.player.es.Domain.UserDomain;
import org.springframework.context.annotation.Bean;

import java.util.List;


public interface UserDao {
    List<UserDomain> select();
    UserDomain getByUserID(String userID);
    UserDomain getByUserName(String userName);
}
