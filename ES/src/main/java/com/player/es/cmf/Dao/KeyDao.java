package com.player.es.cmf.Dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.LinkedHashMap;

public interface KeyDao {
    LinkedHashMap<String,Object> getKeyNumber(String email);
    void insertKey(@Param("email") String email, @Param("keyNumber")Integer keyNumber, @Param("keyTime")Date startTime);
    void deleteKey(String email);
}
