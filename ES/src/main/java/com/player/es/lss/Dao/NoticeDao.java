package com.player.es.lss.Dao;

import com.player.es.Domain.NoticeDomain;
import org.apache.ibatis.annotations.Options;

import java.util.Map;

public interface NoticeDao {
    String isExistId(String noticeId);
    @Options(useGeneratedKeys = true, keyProperty = "noticeId")
    int createNotice(NoticeDomain noticeDomain);
    int editNotice(Map<String,String> map);
}
