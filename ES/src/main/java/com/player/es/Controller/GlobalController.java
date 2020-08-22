package com.player.es.Controller;

import com.player.es.Service.MatchService;
import com.player.es.Utils.ResponseUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GlobalController {
    @Autowired
    MatchService mService;
    @RequestMapping("/api/global/curMatchs")
    public ResponseUnit apiPublicTeamT(@RequestBody Map<String,Object> parse) {
        String season = parse.get("season").toString();
        Integer page = (Integer)parse.get("page"), pageSize = (Integer)parse.get("pageSize");
        return new ResponseUnit(200,"成功",mService.getSeasonAllMatch(season,page*pageSize,pageSize));
    }
}
