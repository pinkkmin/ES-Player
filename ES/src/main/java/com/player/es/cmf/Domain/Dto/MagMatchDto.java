package com.player.es.cmf.Domain.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.player.es.cmf.Domain.POJO.TeamPojo;
import lombok.Data;

import java.util.Date;

@Data
public class MagMatchDto {
    String matchId;
    Integer status;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm")
    Date date;
    String season;
    TeamPojo home;
    TeamPojo away;
}
