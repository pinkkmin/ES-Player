package com.player.es.Domain;

import lombok.Data;

@Data
public class TeamDomain {
    String teamId;      // 球队ID
    String teamName;    // 球队名称
    String teamCity;    // 所在城市
    String teamCoach;   // 教练
}
