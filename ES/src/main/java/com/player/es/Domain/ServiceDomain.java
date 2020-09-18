package com.player.es.Domain;

import lombok.Data;

import java.util.Date;

@Data
public class ServiceDomain {
    String serverId;
    String playerId;
    String teamId;
    Date startTime;
    int startStatus;
}
