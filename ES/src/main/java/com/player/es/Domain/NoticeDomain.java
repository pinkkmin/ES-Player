package com.player.es.Domain;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeDomain {
    String noticeId;    // 公告ID
    String authId;      // 发布者ID
    String playerId;    // 相关球员ID
    String homeId;      // 相关主队ID
    String awayId;      // 相关客队ID
    String title;       // 标题
    String content;     // 内容
    Date noticeDate;          // 发布日期
}
