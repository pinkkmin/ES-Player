package com.player.es.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtProfile implements Serializable {
    /** 封装返回的一些信息
     * 补充一些属性*/
    private String userID;
    private String userName;
    private String email;
    private int role;
}
