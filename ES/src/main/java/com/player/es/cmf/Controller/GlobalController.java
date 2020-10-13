package com.player.es.cmf.Controller;

import com.player.es.Config.MybatisConfig;
import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Dao.KeyDao;
import com.player.es.cmf.Service.MatchService;
import com.player.es.cmf.Service.TeamService;
import com.player.es.lss.Dao.UserDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
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
    @GetMapping("/api/global/seasonList")
    public ResponseUnit getSeasonList() {
        return new ResponseUnit(200,"成功",mService.getSeasonList());
    }
    @GetMapping("/api/global/curSeason")
    public ResponseUnit getCurSeasonName() {

        return new ResponseUnit(200,"成功",mService.getCurSeason());
    }
   /**get match by everyday*/
   @RequestMapping("/api/global/matchByDay")
   public ResponseUnit getMatchByDay(@RequestBody Map<String,String> parse) {
        String season = parse.get("season");
        String month = parse.get("month");
        if(season == null || month==null) return new ResponseUnit(400,"参数错误",null);
       //System.out.println(season+month);
       return  mService.getMatchByDay(season,month);
   }
    /**上传文件处理csv文件*/
    @RequestMapping("/api/global/updateFile")
    /**上传文件的同时 传递JSON参数 使用 RequestPart
     * */
    public ResponseUnit updateFile(@RequestPart("file") MultipartFile file,@RequestPart("match")  Map<String,Object> parse) {
        ResponseUnit res = new ResponseUnit();
        LinkedHashMap<String,Object> data = mService.doUpdateMatchData(file,parse);
        int code = (Integer)data.get("code");
            res.setCode(code);
            res.setMessage((String)data.get("massage"));
        if(code == 200) {
            data.remove("code");
            data.remove("massage");
            res.setData(data);
        }
        return res;
    }
    @RequestMapping("/api/global/getKeyNumber")
    public ResponseUnit getKeyNumber(@RequestBody Map<String,String> map) {
        String email = map.get("email");
       // System.out.println(map);
        int type = Integer.valueOf(map.get("type"));
        return teamService.getKeyNumber(email,type);
    }
    @RequestMapping("/api/global/regKeyNumber")
    public ResponseUnit regKeyNumber(@RequestBody Map<String,String> map) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            String email = map.get("email");
            String userId  = userDao.getUserIdByEmail(email);
            if(userId!=null) return new ResponseUnit(400,"邮箱已被注册,可通过找回密码重新登录",null);
            int type = Integer.valueOf(map.get("type"));
            return teamService.getKeyNumber(email,type);
        }
    }
    @RequestMapping("/api/global/resetPwd")
    public ResponseUnit resetPassWord(@RequestBody Map<String,String> map) {
        return teamService.resetPwd(map);

    }
    @RequestMapping("/api/global/register")
    public ResponseUnit register(@RequestBody Map<String,String> map) {
        return teamService.register(map);
    }
        @RequestMapping("/api/global/playerService")
    public ResponseUnit playerService(@RequestBody Map<String,String> map) {
        String playerId = map.get(("playerId"));
        return new ResponseUnit(200,"", teamService.getPlayerService(playerId));
    }
    @RequestMapping("/api/global/teamInfo")
    public ResponseUnit teamSortInfo(@RequestBody Map<String,String> map) {
        String teamId = map.get("teamId");
        String season = map.get("season");
        return new ResponseUnit(200,"", teamService.getTeamInfoSort(teamId,season));
    }

    @RequestMapping("/api/manager/createMatch")
    public ResponseUnit createMatch(@RequestBody Map<String,Object> map) {
        int status = mService.insertMatch(map);
        if(status <= 0) return new ResponseUnit(400,"","");
        return new ResponseUnit(200,"", "");
    }
}
