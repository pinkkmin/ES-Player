package com.player.es.cmf.Controller;

import com.player.es.Config.MybatisConfig;
import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Domain.Dto.MagMatchDto;
import com.player.es.cmf.Domain.Dto.QueryMatchDto;
import com.player.es.cmf.Service.MatchService;
import com.player.es.cmf.Service.TeamService;
import org.apache.ibatis.session.SqlSession;
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
    @GetMapping("/api/teamList")
    public ResponseUnit apiTeamList() {
        List<Map> data = teamService.getTeamList();
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
    @RequestMapping("/api/home/todayMatch")
    public ResponseUnit getTodayMatchList(@RequestBody Map<String,String> parse) {
        String season = parse.get("season");
        return null;
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
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String,Object>> data = matchDao.queryMatch(item);
            return new ResponseUnit(250,"hello",data);
        }
    }

    // root-赛事管理-编辑球员赛事记录
    @RequestMapping("/api/manager/editMatchData")
    public ResponseUnit magEditMatchData(@RequestBody Map<String,String> parse) {
        return null;
    }
}
