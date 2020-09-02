package com.player.es.cmf.Controller;

import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Service.MatchService;
import com.player.es.cmf.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GlobalController {
    @Autowired
    MatchService mService;
    @Autowired
    TeamService teamService;

    /**FOR Global API
     * api/global/curMatchs
     * api/global/teamMatchs
     **/
    /**本赛季所有赛事**/
    @RequestMapping("/api/global/curMatchs")
    public ResponseUnit apiPublicTeamT(@RequestBody Map<String,Object> parse) {
        String season = parse.get("season").toString();
        Integer page = (Integer)parse.get("page"), pageSize = (Integer)parse.get("pageSize");
        return new ResponseUnit(200,"成功",mService.getSeasonAllMatch(season,page*pageSize,pageSize));
    }
    /**特定球队的本赛季-赛事赛程-按月份分组**/
    @RequestMapping("/api/global/teamMatchs")
    public ResponseUnit getTeamSeasonMatchList(@RequestBody Map<String,String> parse) {
        String season = parse.get("season"), teamId = parse.get("teamId");
        return new ResponseUnit(200,"成功",teamService.getTeamSeasonMatchList(teamId,season));
    }
    /**本赛季-名称**/
    @GetMapping("/api/global/curSeason")
    public ResponseUnit getCurSeasonName() {

        return new ResponseUnit(200,"成功",mService.getCurSeason());
    }
}
