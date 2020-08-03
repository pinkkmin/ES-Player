package com.player.es.Dao;

import com.player.es.Domain.NoticeDomain;

import java.util.List;
import java.util.Map;

public interface NoticeDao {
    List<NoticeDomain> test();
    int createNotice(NoticeDomain noticeDomain);
    int editNotice(Map<String,String> map);
}
