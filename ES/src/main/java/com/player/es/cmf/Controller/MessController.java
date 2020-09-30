package com.player.es.cmf.Controller;

import com.player.es.Domain.MatchDataDomain;
import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Domain.Dto.MagMatchDto;
import com.player.es.cmf.Domain.Dto.MatchDataDto;
import com.player.es.cmf.Domain.Dto.QueryMatchDto;
import com.player.es.cmf.Service.MatchDataService;
import com.player.es.cmf.Service.MatchService;
import com.player.es.cmf.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 一些 分类不太清晰的接口API
 * */
@RestController
public class MessController {
    @Autowired
    TeamService teamService;
    @Autowired
    MatchService matchService;
    @Autowired
    MatchDataService mdService;
    @GetMapping("/api/teamList")
    public ResponseUnit apiTeamList() {
        List<Map> data = teamService.getTeamList();
        //System.out.println("*****");
        return new ResponseUnit(200,"球队列表",data);
    }

    /***首页-HOME-API*/
    //积分榜
    @RequestMapping("/api/home/sort")
    public ResponseUnit getTeamSort(@RequestBody Map<String,String> parse) {
        String season = parse.get("season");
        return new ResponseUnit(200,"成功",teamService.getAllTeamSort(season));
    }

    // 今日赛事
    @GetMapping("/api/home/todayMatch")
    public ResponseUnit getTodayMatchList() {
        return new ResponseUnit(200,"成功",matchService.getTodayMatch());
    }

    /**后台管理-API**/
    // root-赛事管理-编辑赛事基本信息
    @RequestMapping("/api/manager/editMatch")
    public ResponseUnit magEditMatch(@RequestBody MagMatchDto parse) {
        MagMatchDto data = matchService.magEditMatch(parse);
        if(data == null) return new ResponseUnit(250,"未发生修改......",null);
        return new ResponseUnit(200,"修改成功",data);
    }

    // root-赛事管理-查询赛事
    @RequestMapping("/api/manager/queryMatch")
    public ResponseUnit magQueryMatch(@RequestBody QueryMatchDto item) {
            return new ResponseUnit(200,"hello",matchService.queryMatch(item));
    }

    // root-赛事管理-编辑球员赛事记录
    @RequestMapping("/api/manager/editMatchData")
    public ResponseUnit magEditMatchData(@RequestBody MatchDataDto mdd) {
        MatchDataDomain data = mdService.editMatchData(mdd);
        if(data == null) return new ResponseUnit(220,"未发生修改....",null);
        return new ResponseUnit(200,"修改成功",data);
    }
    @RequestMapping("/api/team/info")
    public ResponseUnit getTeamInfo(@RequestBody Map<String, String> map) {
        String teamId = map.get("teamId");
        LinkedHashMap data =  teamService.getInfoById(teamId);
        return new ResponseUnit(200,"数据请求成功....",data);
    }
    @RequestMapping("/api/manager/createPlayer")
    public ResponseUnit createPlayer(@RequestBody Map<String, Object> map) {
        return teamService.createPlayer(map);
    }
    // 处理交易球员
    @RequestMapping("/api/manager/addPlayers")
    public ResponseUnit addPlayers(@RequestBody Map<String, Object> map) {
        return teamService.addPlayers(map);
    }
    // 处理交易球员
    @RequestMapping("/api/manager/dealPlayer")
    public ResponseUnit dealPlayer(@RequestBody Map<String, Object> map) {
        return teamService.dealPlayer(map);
    }
    @RequestMapping("/api/manager/queryNoMatch")
    public ResponseUnit queryNoMatch(@RequestBody Map<String, Object> map) {
        int start = (int)map.get("page")*(int)map.get("pageSize");
        map.put("start",start);
        ResponseUnit res = new ResponseUnit();
        res.setCode(200);
        res.setMessage("");
        res.setData(matchService.queryNoDataMatch(map));
        return res;
    }
    @RequestMapping("/api/manager/insertMatchData")
    public ResponseUnit insertMatchData(@RequestBody Map<String, Object> map) {
        ResponseUnit res = new ResponseUnit(200,"","");
        Map<String, Object> matchInfo = (Map)map.get("match");
        ArrayList<LinkedHashMap<String,Object>> homeData = (ArrayList)map.get("homeData");
        ArrayList<LinkedHashMap<String,Object>> awayData = (ArrayList)map.get("awayData");
        String matchId = (String)matchInfo.get("matchId");
       int code =  matchService.insertMatchData(matchId,homeData,awayData);
       if(code!=1) res.setMessage("错误");
        return res;
    }
}
