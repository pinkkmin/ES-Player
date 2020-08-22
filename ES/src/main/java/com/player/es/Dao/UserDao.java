package com.player.es.Dao;

import com.player.es.Domain.UserDomain;


import java.util.List;
import java.util.Map;


public interface UserDao {
    List<UserDomain> select();
    UserDomain getByUserID(String userID);
    UserDomain getByUserName(String userName);
    int updatePassword(Map<String,String> message);
}
