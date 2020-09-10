package com.player.es.lss.Domain.DTO;

import lombok.Data;

@Data
public class UserDto {
    String userId;
    String userName;
    String userEmail;
    int role;
    String teamId;
}
