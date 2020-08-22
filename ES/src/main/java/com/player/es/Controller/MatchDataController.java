package com.player.es.Controller;

import com.player.es.Service.MatchDataService;
import com.player.es.Service.MatchService;
import com.player.es.Service.TeamService;
import com.player.es.Utils.ResponseUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MatchDataController {
    @Autowired
    MatchDataService mddService;
    @Autowired
    MatchService mService;
    @Autowired
    TeamService teamService;
    /*********Api/public/...********/
    @RequestMapping("/api/public/team/first")
    public ResponseUnit apiPublicTeamF(@RequestBody Map<String,String> parse) {
        String teamId = parse.get("teamId"), season = parse.get("season");
        Map data = new HashMap();
        data.put("team",mService.getTeamSort(teamId,season));
        data.put("radarData",mddService.getRadarData(teamId, season));
        data.put("barData",mddService.getBarData(teamId,season));
        return new ResponseUnit(200,"成功",data);
    }
    @RequestMapping("/api/public/team/second")
    public ResponseUnit apiPublicTeamS(@RequestBody Map<String,String> parse) {
        String teamId = parse.get("teamId"), season = parse.get("season");
        HashMap data = new HashMap();
        data.put("scoreData",teamService.getItemOfAllPlayer(teamId,season,"score"));
        data.put("assistData",teamService.getItemOfAllPlayer(teamId,season,"assist"));
        data.put("boundData",teamService.getItemOfAllPlayer(teamId,season,"bound"));
        data.put("blockData",teamService.getItemOfAllPlayer(teamId,season,"block"));
        data.put("scoreData",teamService.getItemOfAllPlayer(teamId,season,"steal"));
        return new ResponseUnit(200,"成功",data);
    }
    @RequestMapping("/api/public/team/third")
    public ResponseUnit apiPublicTeamT(@RequestBody Map<String,String> parse) {
        String teamId = parse.get("teamId");
        return new ResponseUnit(200,"成功",mService.getTeamAvgOfSeason(teamId));
    }
    @RequestMapping("/api/public/player")
    public ResponseUnit apiPublicPlayer(@RequestBody Map<String,String> parse) {

        return null;
    }
    @RequestMapping("/api/public/match")
    public ResponseUnit apiPublicMatch(@RequestBody Map<String,String> parse) {
        String  matchId = parse.get("matchId");
        return new ResponseUnit(200,"成功",mService.getMatchInfo(matchId));
    }

}
