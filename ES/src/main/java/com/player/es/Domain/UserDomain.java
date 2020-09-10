package com.player.es.Domain;
import lombok.Data;
// 使用@Data注解就不必写setter和getter
// User实体类

@Data
public class UserDomain {
    private String userID;
    private String userName;
    private String passwd;
    private String email;
   // private Date regDate;
    private int role;
    private String teamId;

}
