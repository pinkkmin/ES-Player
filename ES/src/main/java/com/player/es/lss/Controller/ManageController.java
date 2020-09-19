package com.player.es.lss.Controller;

import com.player.es.Domain.NoticeDomain;
import com.player.es.Domain.TeamDomain;
import com.player.es.Utils.ResponseUnit;
import com.player.es.lss.Service.ManageService;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@RestController
@RequestMapping("/api/manager")
public class ManageController {
    @Autowired
    ManageService manageService;

//    修改用户信息
    @RequestMapping("editUser")
    public ResponseUnit editUser(@RequestBody HashMap<String, Object> hashMap){
        return ResponseUnit.succ(200,"修改成功", manageService.editUser(hashMap));
    }

//    后台管理：查找用户信息
    @RequestMapping("queryUser")
    public ResponseUnit queryUser(@RequestBody HashMap<String, Object> hashMap){
        return ResponseUnit.succ(200,"查找成功",manageService.queryUser(hashMap));
    }

//    后台管理：新建球队
    @RequestMapping("createTeam")
    public ResponseUnit createTeam(@RequestBody HashMap<String,Object> hashMap){
        LinkedHashMap<String,Object> linkedHashMap=manageService.createTeam(hashMap);
        if((int)linkedHashMap.get("status")==1){
            return ResponseUnit.succ(200,"新增成功",linkedHashMap.get("data"));
        }
        else{
            return ResponseUnit.fail("新增失败");
        }
    }
//    后台管理：删除公告
    @RequestMapping("deleteNotice")
    public ResponseUnit deleteNotice(@RequestBody HashMap<String,String> hashMap){
        String noticeId=hashMap.get("noticeId");
        int status=manageService.deleteNotice(noticeId);
        if(status>0){
            return ResponseUnit.succ("成功删除");
        }
        else{
            return ResponseUnit.fail("删除失败");
        }
    }

//    后台管理：编辑公告
    @RequestMapping("editNotice")
    public ResponseUnit editNotice(@RequestBody HashMap<String,Object> hashMap){
        int status = manageService.editNotice(hashMap);
        if(status>0){
            return ResponseUnit.succ("更新成功");
        }
        else
            return ResponseUnit.fail("更新失败");
    }

//    后台管理：查找公告
    @RequestMapping("queryNotice")
    public ResponseUnit queryNotice(@RequestBody HashMap<String,Object> hashMap){
        return ResponseUnit.succ(200,"查找成功",manageService.queryNotice(hashMap));
    }

//    后台管理：发布公告？？不完整
    @RequestMapping("publish")
    public ResponseUnit publish(@RequestBody NoticeDomain noticeDomain){
        return null;
    }

//    后台管理：查询service表中与team_id对应的数据-按时间排序
    @RequestMapping("service")
    public ResponseUnit getServiceByTeamId(@RequestBody HashMap<String,Object> hashMap){
        return ResponseUnit.succ(200,"查询成功",manageService.getServiceByTeamId(hashMap));
    }

//     后台管理：查询Service记录
    @RequestMapping("queryService")
    public ResponseUnit queryService(@RequestBody HashMap<String,Object> hashMap){
        return ResponseUnit.succ(200,"查询成功",manageService.queryService(hashMap));
  //  return null;
    }
}
