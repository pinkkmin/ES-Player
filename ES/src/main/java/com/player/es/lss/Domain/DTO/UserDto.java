package com.player.es.lss.Domain.DTO;

import lombok.Data;

@Data
public class UserDto {
    String userId;
    String email;
    int type;
    String teamId;
}
