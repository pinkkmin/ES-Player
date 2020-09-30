package com.player.es.cmf.Service;

import com.player.es.Domain.MatchDataDomain;
import com.player.es.Utils.GlobalConstDataUtils;
import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Dao.MatchDataDao;
import com.player.es.cmf.Dao.TeamDao;
import com.player.es.Domain.TeamDomain;
import com.player.es.Config.MybatisConfig;
import com.player.es.cmf.Domain.Dto.MatchDataDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class MatchDataService {
    private GlobalConstDataUtils globalConstData;
    private  DecimalFormat df = new DecimalFormat("#.0");
    public MatchDataService(){
        globalConstData = new GlobalConstDataUtils();
    }
    // 获取radar雷达图数据
    public LinkedHashMap getRadarData(String teamId, String season) {
        LinkedHashMap data = new LinkedHashMap();
        LinkedHashMap maxData = getMaxItemOfTeam(season);
        maxData.remove("maxFoul");
        maxData.remove("maxTurnOver");
        data.put("maxData",maxData);
        data.put("data",getAvgItemOfTeamList(teamId,season));
        return data;
    }
    public LinkedHashMap getBarData(String teamId, String season) {
        LinkedHashMap data = getDataTitle();
        data.put("data",getItemOfBarData(teamId,season));
        return data;
    }
    // 获取-赛季-各项球队场均-最高数据
    public LinkedHashMap<String,Double> getMaxItemOfTeam(String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            LinkedHashMap data = new LinkedHashMap<String,Double>();
            //获取所有已结束球队的参赛次数
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            List<LinkedHashMap> team = matchDao.getGameCount(season);
            //获取各个球队已结束的比赛的数据总和
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            List<LinkedHashMap<String,Object>> teamSum = matchDataDao.getSeasonSum(season);
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

            data.put("maxScore",Double.valueOf(df.format(maxScore)));
            data.put("maxAssist",Double.valueOf(df.format(maxAssist)));
            data.put("maxBound",Double.valueOf(df.format(maxBound)));
            data.put("maxBlock",Double.valueOf(df.format(maxBlock)));
            data.put("maxSteal",Double.valueOf(df.format(maxSteal)));
            data.put("maxTurnOver",Double.valueOf(df.format(maxTurnOver)));
            data.put("maxFoul",Double.valueOf(df.format(maxFoul)));
            return data;
        }
    }
    // 获取-赛季-球队-场均各数据项
    // 得分-助攻-篮板-盖帽-抢断-盖帽-失误-犯规
    Double getDoubleByObject(Object value,int game){
        Double data = Double.valueOf(value.toString())/game;
        return  Double.valueOf(df.format(data));
    }
    public ArrayList getAvgItemData(String teamId, String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            int game = matchDao.getTeamGameCount(teamId, season);
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            Map data =  matchDataDao.getTeamSum(teamId, season);
            ArrayList value = new ArrayList<Double>();
            for (String name: globalConstData.getEnNameList()
                 ) {
                Double item = getDoubleByObject(data.get(name),game);
                value.add(item);
            }
            return value;
        }

    }
    public LinkedHashMap getAvgItemOfTeam(String teamId, String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            List value = getAvgItemData(teamId, season);
            LinkedHashMap item = new LinkedHashMap();  // return data
            value.remove(6);
            value.remove(5);
            item.put("value",value);
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            TeamDomain team = teamDao.getTeamInfo(teamId);
            item.put("name",team.getTeamName());
            return item;
        }
    }
    public ArrayList getAvgItemOfTeamList(String teamId, String season) {
            ArrayList reData = new ArrayList<Map>();
            reData.add(getAvgItemOfTeam(teamId,season));
            return reData;

    }
    // 获取球队对比数据
    public LinkedHashMap getDataTitle() {
        LinkedHashMap data = new LinkedHashMap();
        data.put("title","赛季数据对比");
        data.put("min",0);
        data.put("max",120);
        return data;
    }
    public ArrayList getAvgItem(String season){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao  = sqlSession.getMapper(MatchDao.class);
            int matchCount = matchDao.getMatchCount(season);
            MatchDataDao mdd = sqlSession.getMapper(MatchDataDao.class);
            Map data =   mdd.getAllTeamSum(season);
            ArrayList reList = new ArrayList();
            for (String name: globalConstData.getEnNameList()
            ) {
                Double item = getDoubleByObject(data.get(name),matchCount*2);
                reList.add(item);

            }
            return reList;
        }
    }
    public ArrayList getItemOfBarData(String teamId, String season) {
        ArrayList dataList = new ArrayList<List<String>>();
        ArrayList dataFirst = new ArrayList<String>();
        dataFirst.add("item");
        for (String name : globalConstData.getCnNameList() ) {
            dataFirst.add(name);
        }
        dataList.add(dataFirst);
        List dataTeam =  getAvgItemData(teamId,season);
        dataTeam.add(0,"球队");
        List dataMax = new ArrayList<String>();
        dataMax.add("最高");
        Map maxItem = getMaxItemOfTeam(season);
        for (String name:globalConstData.getMax7NameList()
             ) {
            dataMax.add(maxItem.get(name));
        }
        List dataAvg = getAvgItem(season);
        dataAvg.add(0,"平均");
        dataList.add(dataTeam);
        dataList.add(dataMax);
        dataList.add(dataAvg);
        return dataList;
    }
    /**root-修改赛事记录数据**/
    public MatchDataDomain editMatchData(MatchDataDto mdd) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
           int data = matchDataDao.editMatchData(mdd);
           String matchId = mdd.getMatchId();
           int homeScore = matchDataDao.sumOfMatch(matchId,1);
           int awayScore = matchDataDao.sumOfMatch(matchId,0);
           matchDataDao.updateMatchScore(matchId,homeScore,awayScore);
           if(data==0)return null;
            sqlSession.commit();
           return matchDataDao.queryMatchData(mdd.getMatchId(), mdd.getPlayerId());

        }
    }
}
