package com.player.es.lss.Dao;

import java.util.HashMap;
import java.util.Map;

public interface TeamManageDao {
//    球队管理：编辑球队信息
    int editTeam(Map<String,String> map);
//    球队管理：编辑球员信息
    int editPlayer(Map<String,Object> map);
//    球队管理：处理球员----------------待定
//    int dealPlayer()

}
