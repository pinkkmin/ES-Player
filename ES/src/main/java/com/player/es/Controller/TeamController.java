package com.player.es.Controller;

import com.player.es.Service.TeamService;
import com.player.es.Utils.ResponseUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TeamController {
    @Autowired
    TeamService teamService;
    @GetMapping("/api/teamList")
    public ResponseUnit apiTeamList() {
        List<Map> data = teamService.getTeamList();
        return new ResponseUnit(200,"球队列表",data);
    }
    @GetMapping("/api/public/team/first")
    public ResponseUnit apiTeamFirst(@RequestBody Map<String,String> map) {
        String teamId = map.get("teamId");

        return new ResponseUnit();
    }
    /*积分榜*/
    @RequestMapping("/api/home/sort")
    public ResponseUnit apiPublicTeamT(@RequestBody Map<String,String> parse) {
        String season = parse.get("season");
        return new ResponseUnit(200,"成功",teamService.getAllTeamSort(season));
    }
    /*特定球队的本赛季-赛事赛程-按月份分组*/
    @RequestMapping("/api/global/teamMatchs")
    public ResponseUnit getTeamSeasonMatchList(@RequestBody Map<String,String> parse) {
        String season = parse.get("season"), teamId = parse.get("teamId");
        return new ResponseUnit(200,"成功",teamService.getTeamSeasonMatchList(teamId,season));
    }
}
