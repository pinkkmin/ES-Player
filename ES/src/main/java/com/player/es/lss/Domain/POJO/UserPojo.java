package com.player.es.lss.Domain.POJO;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class UserPojo {
    String userId;
    String userName;
    String userEmail;
    int role;
    LinkedHashMap<String,String> team;

    public UserPojo(String userId,String userName,String userEmail,int role,String teamId,String teamName){
        this.userEmail=userEmail;
        this.userId=userId;
        this.userName=userName;
        this.role=role;
        this.team=new LinkedHashMap<>();
        this.team.put("teamId",teamId);
        this.team.put("teamName",teamName);
    }
}
