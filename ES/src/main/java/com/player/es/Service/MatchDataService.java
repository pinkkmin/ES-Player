package com.player.es.Service;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.MatchDao;
import com.player.es.Dao.MatchDataDao;
import com.player.es.Dao.TeamDao;
import com.player.es.Domain.TeamDomain;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class MatchDataService {
    public String getTeamName(String teamId) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            TeamDomain team = teamDao.getTeamInfo(teamId);
            return team.getTeamName();
        }
    }
    // 获取radar雷达图数据
    public Map getRadarData(String teamId, String season) {
        Map data = new HashMap();
        Map maxData = getMaxItemOfTeam(season);
        maxData.remove("maxFoul");
        maxData.remove("maxTurnOver");
        data.put("maxData",maxData);
        data.put("data",getAvgItemOfTeam(teamId,season));
        return data;
    }
    public Map getBarData(String teamId, String season) {
        Map data = getDataTitle();
        data.put("data",getItemOfBarData(teamId,season));
        return data;
    }
    // 获取-赛季-各项球队场均-最高数据
    public Map getMaxItemOfTeam(String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            Map data = new HashMap<String,Double>();
            //获取所有已结束球队的参赛次数
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            List<Map> team = matchDao.getGameCount(season);
            //获取各个球队已结束的比赛的数据总和
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            List<Map> teamSum = matchDataDao.getSeasonSum(season);
            Double maxScore= 0.0,maxAssist = 0.0,maxBound = 0.0 ,maxSteal= 0.0,maxBlock =0.0,maxFoul = 0.0,maxTurnOver = 0.0;
            for( int index = 0; index < team.size();index++) {
                Double game = Double.valueOf(team.get(index).get("game").toString());
                teamSum.get(index).put("game",game);
            }
            for(int index = 0;index<teamSum.size();index++) {
                Double game = Double.valueOf(teamSum.get(index).get("game").toString());
                maxScore = Math.max(maxScore,Double.valueOf(teamSum.get(index).get("score").toString())/game);
                maxAssist = Math.max(maxAssist,Double.valueOf(teamSum.get(index).get("assist").toString())/game);
                maxBlock = Math.max(maxBlock,Double.valueOf(teamSum.get(index).get("block").toString())/game);
                maxSteal = Math.max(maxSteal,Double.valueOf(teamSum.get(index).get("steal").toString())/game);
                maxBound = Math.max(maxBound,Double.valueOf(teamSum.get(index).get("bound").toString())/game);
                maxFoul = Math.max(maxFoul,Double.valueOf(teamSum.get(index).get("foul").toString())/game);
                maxTurnOver = Math.max(maxTurnOver,Double.valueOf(teamSum.get(index).get("turnover").toString())/game);
            }
            DecimalFormat df = new DecimalFormat("#.0");
            data.put("maxScore",df.format(maxScore));
            data.put("maxAssist",df.format(maxAssist));
            data.put("maxBound",df.format(maxBound));
            data.put("maxBlock",df.format(maxBlock));
            data.put("maxSteal",df.format(maxSteal));
            data.put("maxTurnOver",df.format(maxTurnOver));
            data.put("maxFoul",df.format(maxFoul));
            return data;
        }
    }
    // 获取-赛季-球队-场均各数据项
    // 得分-助攻-篮板-盖帽-抢断-盖帽-失误-犯规
    public List getAvgItemData(String teamId, String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            int game = matchDao.getTeamGameCount(teamId, season);
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            Map data =  matchDataDao.getTeamSum(teamId, season);
            List value = new ArrayList<Double>();
            Double score = Double.valueOf(data.get("score").toString()) / game;
            Double assist = Double.valueOf(data.get("assist").toString()) / game;
            Double bound = Double.valueOf(data.get("bound").toString()) / game;
            Double steal = Double.valueOf(data.get("steal").toString()) / game;
            Double block = Double.valueOf(data.get("block").toString()) / game;
            Double foul = Double.valueOf(data.get("foul").toString()) / game;
            Double turnover = Double.valueOf(data.get("turnover").toString()) / game;
            DecimalFormat df = new DecimalFormat("#.0");
            value.add(df.format(score));
            value.add(df.format(assist));
            value.add(df.format(bound));
            value.add(df.format(block));
            value.add(df.format(steal));
            value.add(df.format(turnover));
            value.add(df.format(foul));
            return value;
        }

    }
    public List getAvgItemOfTeam(String teamId, String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            List value = getAvgItemData(teamId, season);
            Map item = new LinkedHashMap();  // return data
            value.remove(6);
            value.remove(5);
            item.put("value",value);
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            TeamDomain team = teamDao.getTeamInfo(teamId);
            item.put("name",team.getTeamName());
            List reData = new ArrayList<Map>();
            reData.add(item);
            return reData;
        }
    }
    // 获取球队对比数据
    public Map getDataTitle() {
        Map data = new LinkedHashMap();
        data.put("title","赛季数据对比");
        data.put("min",0);
        data.put("max",120);
        return data;
    }
    public List getAvgItem(String season){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao team = sqlSession.getMapper(TeamDao.class);
            int teamCount = team.getTeamCount();
            MatchDao matchDao  = sqlSession.getMapper(MatchDao.class);
            int matchCount = matchDao.getMatchCount(season);
            MatchDataDao mdd = sqlSession.getMapper(MatchDataDao.class);
            Map data =   mdd.getAllTeamSum(season);
            List reList = new ArrayList();
            DecimalFormat df = new DecimalFormat("#.0");
            Double score = Double.valueOf(data.get("score").toString())/(matchCount);
            Double assist = Double.valueOf(data.get("assist").toString())/(matchCount);
            Double bound = Double.valueOf(data.get("bound").toString())/(matchCount);
            Double block = Double.valueOf(data.get("block").toString())/(matchCount);
            Double steal = Double.valueOf(data.get("steal").toString())/(matchCount);
            Double turnover = Double.valueOf(data.get("turnover").toString())/(matchCount);
            Double foul = Double.valueOf(data.get("foul").toString())/(matchCount);
            reList.add(df.format(score));
            reList.add(df.format(assist));
            reList.add(df.format(bound));
            reList.add(df.format(block));
            reList.add(df.format(steal));
            reList.add(df.format(turnover));
            reList.add(df.format(foul));
            return reList;
        }
    }
    public List getItemOfBarData(String teamId, String season) {
        List dataList = new ArrayList<List<String>>();
        List dataFirst = new ArrayList<String>();
        dataFirst.add("item"); dataFirst.add("得分");
        dataFirst.add("助攻"); dataFirst.add("篮板");
        dataFirst.add("抢断"); dataFirst.add("盖帽");
        dataFirst.add("失误"); dataFirst.add("犯规");
        dataList.add(dataFirst);
        List dataTeam =  getAvgItemData(teamId,season);
        dataTeam.add(0,"球队");
        List dataMax = new ArrayList<String>();
        dataMax.add("最高");
        Map maxItem = getMaxItemOfTeam(season);
        dataMax.add(maxItem.get("maxScore"));  dataMax.add(maxItem.get("maxAssist"));
        dataMax.add(maxItem.get("maxBound"));  dataMax.add(maxItem.get("maxBlock"));
        dataMax.add(maxItem.get("maxSteal"));  dataMax.add(maxItem.get("maxTurnOver"));
        dataMax.add(maxItem.get("maxFoul"));
        List dataAvg = getAvgItem(season);
        dataAvg.add(0,"平均");
        dataList.add(dataTeam);
        dataList.add(dataMax);
        dataList.add(dataAvg);
        return dataList;
    }
    public Map getCompareTeam(String teamId, String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            Map demo = matchDataDao.getTeamSum(teamId,season);
            System.out.println(demo);
        }
        return null;
    }
    //获取-球员-各项场均数据
    public Map getAvgItemOfPlayer(String playerId, String season) {
        return null;
    }
    // 球员-各项数据最高
    public Map getMaxItemOfPlayer(String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            return matchDataDao.getMaxItemOfPlayer(season);
        }
    }
}
