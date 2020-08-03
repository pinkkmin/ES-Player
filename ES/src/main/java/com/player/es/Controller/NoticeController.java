package com.player.es.Controller;

import cn.hutool.core.date.DateTime;
import com.player.es.Domain.NoticeDomain;
import com.player.es.Service.NoticeService;
import com.player.es.Utils.ResponseUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @GetMapping("/index")
    public ResponseUnit getAllNotice(){
        List<NoticeDomain> all = noticeService.getALL();
        return ResponseUnit.succ(all);
    }
//    ------------------------创建公告---------------------------------------
    @RequestMapping("/create")
    public ResponseUnit createNotice(@RequestBody HashMap<String,String> map){
        NoticeDomain noticeDomain = new NoticeDomain();
        noticeDomain.setAuthId(map.get("authId"));
        noticeDomain.setTitle(map.get("title"));
        noticeDomain.setHomeId(map.get("homeId"));
        noticeDomain.setAwayId(map.get("awayId"));
        noticeDomain.setContent(map.get("content"));
        noticeDomain.setNoticeDate(new Date());
        noticeDomain.setPlayerId(map.get("playerId"));
        noticeDomain.setNoticeId("1234567");
        int work = noticeService.createNotice(noticeDomain);
        if(work > 0){
            return ResponseUnit.succ(200,"添加成功", "");
        }
        else
            return ResponseUnit.fail("添加失败");
    }
//    ------------------------创建公告---------------------------------------
//    --------------------------编辑公告--------------------------------------
    @RequestMapping("/edit")
    public ResponseUnit editNotice(@RequestBody HashMap<String,String> hashMap){
        int work = noticeService.editNotice(hashMap);
        if(work > 0){
            return ResponseUnit.succ(200,"修改成功", "");
        }
        else
            return ResponseUnit.fail("修改失败");
    }
//    --------------------------编辑公告--------------------------------------
}
