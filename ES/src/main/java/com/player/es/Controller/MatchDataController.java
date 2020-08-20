package com.player.es.Controller;

import com.player.es.Service.MatchDataService;
import com.player.es.Service.MatchService;
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
        Map data = new HashMap();
        
        return new ResponseUnit(200,"成功",data);
    }
    @RequestMapping("/api/public/team/third")
    public ResponseUnit apiPublicTeamT(@RequestBody Map<String,String> parse) {

        return null;
    }
    @RequestMapping("/api/public/player")
    public ResponseUnit apiPublicPlayer(@RequestBody Map<String,String> parse) {

        return null;
    }
    @RequestMapping("/api/public/match")
    public ResponseUnit apiPublicMatch(@RequestBody Map<String,String> parse) {

        return null;
    }

}
