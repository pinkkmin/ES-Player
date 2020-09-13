package com.player.es.cmf.Controller;

import com.player.es.Utils.FileUtils;
import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Service.MatchService;
import com.player.es.cmf.Service.TeamService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
}
