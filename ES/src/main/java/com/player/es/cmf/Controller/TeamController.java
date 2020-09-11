package com.player.es.cmf.Controller;

import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
/** API for manager team
 *  新建球员信息，PS:补充头像上传功能
 *  球队数据分析-过去七场比赛情况
 *  球队数据分析-本赛季比赛情况
 *  球队数据分析-本赛季比赛情况
 *  数据分析-球队对比
 *  数据分析-球员对比
 */
@RestController
public class TeamController {
    @Autowired
    TeamService teamService;

    @RequestMapping("/api/team/createPlayer")
    public ResponseUnit createPlayer(@RequestBody Map<String, String> map){
        return null;
    }

    @RequestMapping("/api/team/analysis/lastSeven")
    public ResponseUnit lastSevenMatch(@RequestBody Map<String, String> map){
        String teamId = map.get("teamId"), season= map.get("season");
        return new ResponseUnit(200,"数据请求成功....",teamService.lastSevenMatch(teamId,season));
    }

    @RequestMapping("/api/team/analysis/lastSeason")
    public ResponseUnit lastSeasonMatch(@RequestBody Map<String, String> map){
        String teamId = map.get("teamId"), season= map.get("season");
        return new ResponseUnit(200,"数据请求成功....",teamService.lastSeasonMatch(teamId,season));
    }

    @RequestMapping("/api/team/analysis/season")
    public ResponseUnit lastCurSeasonMatch(@RequestBody Map<String, String> map){
        String teamId = map.get("teamId"),season=map.get("season");
        return new ResponseUnit(200,"数据请求成功....",teamService.getTeamSeasonAvgList(teamId,season));
    }

    @RequestMapping("/api/team/compare/teams")
    public ResponseUnit compareTeam(@RequestBody Map<String, String> map){
        String homeId = map.get("home"), awayId = map.get("away"),season = map.get("season");
        return new ResponseUnit(200,"数据请求成功....",teamService.compareTeam(homeId,awayId,season));
    }

    @RequestMapping("/api/team/compare/players")
    public ResponseUnit comparePlayer(@RequestBody Map<String, String> map){
        String homePlayerId = map.get("homePlayerId"), awayPlayerId = map.get("awayPlayerId"),season = map.get("season");
        LinkedHashMap data = teamService.comparePlayer(homePlayerId,awayPlayerId,season);
        return new ResponseUnit(200,"数据请求成功....",data);
    }
    /**返回球队的阵容*/
    @RequestMapping("/api/team/playerArray")
    public ResponseUnit getPlayerArray(@RequestBody Map<String, String> map) {
        String teamId = map.get("teamId");
        String season = map.get("season");
        ArrayList<LinkedHashMap<String,Object>> data =  teamService.getPlayerArray(teamId,season);
        return new ResponseUnit(200,"数据请求成功....",data);
    }
}
