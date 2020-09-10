package com.player.es.lss.Controller;

import com.player.es.Utils.ResponseUnit;
import com.player.es.lss.Service.TeamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/team")
public class TeamManageController {
    @Autowired
    TeamManageService teamManageService;

//    球队管理：编辑球队
    @RequestMapping("editTeam")
    public ResponseUnit editTeam(@RequestBody HashMap<String,String> hashMap){
        int status = teamManageService.editTeam(hashMap );
        if(status>0){
            return ResponseUnit.succ("修改成功");
        }
        else{
            return ResponseUnit.fail("修改失败");
        }
    }
//    球队管理：编辑球员
    @RequestMapping("editPlayer")
    public ResponseUnit editPlayer(@RequestBody HashMap<String,Object> hashMap){
        int status = teamManageService.editPlayer(hashMap);
        if(status>0){
            return ResponseUnit.succ("修改成功");
        }
        else
            return ResponseUnit.fail("修改失败");
    }
}
