package com.player.es.cmf.Domain.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MatchPojo {
    String matchId;
    @DateTimeFormat(pattern="yyyy年MM月dd日 hh:mm")
    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm" )
    Date  date;
    String homeId;
    String awayId;
    String awayName;
    String homeName;
    String status;
    Integer homeScore;
    Integer awayScore;
}
