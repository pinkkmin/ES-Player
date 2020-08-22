package com.player.es.cmf.Domain.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.player.es.cmf.Domain.POJO.TeamPojo;
import lombok.Data;

import java.util.Date;
@Data
public class QueryMatchDto {
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    Date date;
    String season;
    String  homeId;
    String awayId;
    Integer page;
    Integer pageSize;
}
