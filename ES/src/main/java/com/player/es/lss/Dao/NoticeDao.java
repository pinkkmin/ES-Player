package com.player.es.lss.Dao;

import com.player.es.Domain.NoticeDomain;

import java.util.Map;

public interface NoticeDao {
    int createNotice(NoticeDomain noticeDomain);
    int editNotice(Map<String,String> map);
}
