package com.player.es.lss.Controller;

import com.player.es.Domain.UserDomain;
import com.player.es.Utils.ResponseUnit;
import com.player.es.lss.Service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/global")
public class LGlobalController {
    @Autowired
    GlobalService globalService;

//    后台管理：返回指定数量用户列表
    @RequestMapping("/userList")
    public ResponseUnit getUserList(@RequestBody HashMap<String, Integer> hashMap){
        int num = hashMap.get("pageSize")*hashMap.get("page");

        return ResponseUnit.succ(200, "返回成功", globalService.getActualNumUser(num));
    }

//    返回所有球队信息列表
    @GetMapping("/teamList")
    public ResponseUnit getTeamList(){
        return ResponseUnit.succ(200, "获取成功", globalService.getAllTeam());
    }

//    返回特定球队-所有球员列表
    @RequestMapping("/playerList")
    public ResponseUnit getTeamPlayer(@RequestBody HashMap<String, String> hashMap){
        String teamId = hashMap.get("teamId");
        return ResponseUnit.succ(200, "获取成功", globalService.getTeamPlayer(teamId));
    }

//    返回指定赛事主客队球员信息
    @RequestMapping("/matchPlayerList")
    public ResponseUnit getMatchPlayer(@RequestBody HashMap<String, String> hashMap){
        return ResponseUnit.succ(200,"获取成功", globalService.getMatchPlayer(hashMap.get("matchId")));
    }

//    返回所有球队所有球员信息列表，按照球队分组
    @GetMapping("/playerListByTeam")
    public ResponseUnit getPlayerListGroupByTeam(){
        return ResponseUnit.succ(200,"获取成功", globalService.getAllTeamPlayer());
    }

//    返回指定数量的球员id和姓名
    @RequestMapping("/allPlayerList")
    public ResponseUnit getAllPlayerList(@RequestBody HashMap<String, Integer> hashMap){
        int num = hashMap.get("pageSize")*hashMap.get("page");
        return ResponseUnit.succ(200,"获取成功", globalService.getAllPlayerList(num));
    }

//    获取所有球队-所有公告
    @RequestMapping("/allNotices")
    public ResponseUnit getAllNotices(@RequestBody HashMap<String, Integer> hashMap){
        int num = hashMap.get("page")*hashMap.get("pageSize");
        return ResponseUnit.succ(200, "获取成功",globalService.getAllNotices(num));
    }

//
}
