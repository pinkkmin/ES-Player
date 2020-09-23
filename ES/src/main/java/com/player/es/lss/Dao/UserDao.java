package com.player.es.lss.Dao;

import com.player.es.Domain.UserDomain;


import java.util.LinkedHashMap;
import java.util.Map;


public interface UserDao {
    UserDomain getByUserID(String userID);
    UserDomain getByUserName(String userName);
    String getPwdByUserID(String userId);
    int updatePassword(Map<String,String> message);
    int updateEmail(Map<String,String> map);
    int altUserName(String userId,String userName);
    int altPasswd(String userId,String passwd);
    boolean register(Map<String,String> map);
    int resetPasswd(Map<String,String> map);
    LinkedHashMap<String,Object> getUserInformation(String userId);
    ////
    String getUserIdByEmail(String userEmail);
}
