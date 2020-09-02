package com.player.es.cmf.Controller;
/**
 *  FOR PUBLIC PART API
 *  api/public/team/first
 *  api/public/team/second
 *  api/public/team/third
 *  api/public/player
 *  api/public/match
 *  create by cmf
 */

import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Service.MatchService;
import com.player.es.cmf.Service.TeamService;
import com.player.es.cmf.Service.MatchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RequestMapping("/api/public/")
@RestController
public class PublicController {
    @Autowired
    MatchDataService mddService;
    @Autowired
    MatchService mService;
    @Autowired
    TeamService teamService;

    /**
     * Description: 某球队的主页-包含赛程、阵容、数据
     * @param parse { teamId: 'cba2020005'，season:'2019-2020'}
     */
    @RequestMapping("team/first")
    public ResponseUnit apiPublicTeamF(@RequestBody Map<String,String> parse) {
        String teamId = parse.get("teamId"), season = parse.get("season");
        Map data = new HashMap();
        data.put("team",mService.getTeamSort(teamId,season));
        data.put("radarData",mddService.getRadarData(teamId, season));
        data.put("barData",mddService.getBarData(teamId,season));
        return new ResponseUnit(200,"成功",data);
    }

    /**
     * Description: 球队主页中-第二个卡片的数据
     * @param parse { teamId: 'cba2020005'，season:"2019-2020”}
     */
    @RequestMapping("team/second")
    public ResponseUnit apiPublicTeamS(@RequestBody Map<String,String> parse) {
        String teamId = parse.get("teamId"), season = parse.get("season");
        HashMap data = new HashMap();
        data.put("scoreData",teamService.getItemOfAllPlayer(teamId,season,"score"));
        data.put("assistData",teamService.getItemOfAllPlayer(teamId,season,"assist"));
        data.put("boundData",teamService.getItemOfAllPlayer(teamId,season,"bound"));
        data.put("blockData",teamService.getItemOfAllPlayer(teamId,season,"block"));
        data.put("stealData",teamService.getItemOfAllPlayer(teamId,season,"steal"));
        return new ResponseUnit(200,"成功",data);
    }

    /**
     * Description：球队主页中-第三个卡片的数据
     * @param parse { teamId: 'cba2020005' }
     */
    @RequestMapping("team/third")
    public ResponseUnit apiPublicTeamT(@RequestBody Map<String,String> parse) {
        String teamId = parse.get("teamId");
        return new ResponseUnit(200,"成功",mService.getTeamAvgOfSeason(teamId));
    }

    /**
     *Description:
     * @param parse { "teamId": "" }
     */
    @RequestMapping("player")
    public ResponseUnit apiPublicPlayer(@RequestBody Map<String,String> parse) {
        String playerId = parse.get("playerId"),season=parse.get("season");
        return new ResponseUnit(200,"成功",teamService.getPublicPlayer(playerId,season));
    }

    @RequestMapping("player/season")
    public ResponseUnit getPlayerSeasonDataList(@RequestBody Map<String,String> parse) {
        String playerId = parse.get("playerId"),season=parse.get("season");
        return new ResponseUnit(200,"成功",teamService.getPlayerSeasonDataList(playerId,season));
    }

    /**
     *Description:
     * @param parse { "matchId": "" }
     */
    @RequestMapping("match")
    public ResponseUnit apiPublicMatch(@RequestBody Map<String,String> parse) {
        String  matchId = parse.get("matchId");
        return new ResponseUnit(200,"成功",mService.getMatchInfo(matchId));
    }

}
