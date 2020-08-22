package com.player.es.lss.Dao;

import com.player.es.Domain.UserDomain;


import java.util.Map;


public interface UserDao {
    UserDomain getByUserID(String userID);
    UserDomain getByUserName(String userName);
    int updatePassword(Map<String,String> message);
}
