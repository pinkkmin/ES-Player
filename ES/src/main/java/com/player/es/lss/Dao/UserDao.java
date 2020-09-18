package com.player.es.lss.Dao;

import com.player.es.Domain.UserDomain;


import java.util.LinkedHashMap;
import java.util.Map;


public interface UserDao {
    UserDomain getByUserID(String userID);
    UserDomain getByUserName(String userName);
    int updatePassword(Map<String,String> message);
    int updateEmail(Map<String,String> map);
    boolean register(Map<String,Object> map);
    boolean resetPasswd(Map<String,String> map);
    LinkedHashMap<String,Object> getUserInformation(String userId);
    String getUserIdByEmail(String userEmail);
}
