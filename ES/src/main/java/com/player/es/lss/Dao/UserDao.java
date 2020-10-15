package com.player.es.lss.Dao;

import com.player.es.Domain.UserDomain;
import org.apache.ibatis.annotations.Param;


import java.util.LinkedHashMap;
import java.util.Map;


public interface UserDao {
    UserDomain getByUserID(String userID);
    UserDomain getByUserName(String userName);
    String getPwdByUserID(String userId);
    int updatePassword(Map<String,String> message);
    int updateEmail(Map<String,String> map);
    int altUserName(@Param("userId") String userId,@Param("userName") String userName);
    int altTeam(@Param("userId") String userId,@Param("teamId") String teamId);
    int altPasswd(String userId,String passwd);
    boolean register(Map<String,String> map);
    int resetPasswd(Map<String,String> map);
    LinkedHashMap<String,Object> getUserInformation(String userId);
    ////
    String getUserIdByEmail(String userEmail);
    int delUser(String userId);
}
