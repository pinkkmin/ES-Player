package com.player.es.cmf.Service;

import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Dao.MatchDataDao;
import com.player.es.cmf.Domain.Dto.MagMatchDto;
import com.player.es.cmf.Domain.POJO.MatchDataPojo;
import com.player.es.cmf.Domain.POJO.TeamComparePojo;
import com.player.es.Config.MybatisConfig;
import com.player.es.cmf.Domain.POJO.MagMatchPojo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MatchService {
    public LinkedHashMap getTeamSort(String teamId, String season) {
        List<LinkedHashMap> team = getALLTeamSort(season);
        for (LinkedHashMap item : team
        ) {
            if (teamId.equals(item.get("teamId"))) return item;
        }
        return null;
    }
    public List<LinkedHashMap> getALLTeamSort(String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            List<LinkedHashMap> demo = matchDao.getAllTeamSort(season);
            return demo ;
        }
    }
    // 球队-按赛季分组-各项数据平均
    void doAvgItem(HashMap<String,Object> data,int game) {
        DecimalFormat df = new DecimalFormat("#.0");
        Double score = Double.valueOf(data.get("score").toString())/game;
        data.put("score",Double.valueOf(df.format(score)));
        Double assist = Double.valueOf(data.get("assist").toString())/game;
        data.put("assist",Double.valueOf(df.format(assist)));
        Double bound = Double.valueOf(data.get("bound").toString())/game;
        data.put("bound",Double.valueOf(df.format(bound)));
        Double block = Double.valueOf(data.get("block").toString())/game;
        data.put("block",Double.valueOf(df.format(block)));
        Double steal = Double.valueOf(data.get("steal").toString())/game;
        data.put("steal",Double.valueOf(df.format(steal)));
        Double foul = Double.valueOf(data.get("foul").toString())/game;
        data.put("foul",Double.valueOf(df.format(foul)));
        Double free = Double.valueOf(data.get("free").toString())/game;
        data.put("free",Double.valueOf(df.format(free)));
        Double turnover = Double.valueOf(data.get("turnover").toString())/game;
        data.put("turnover",Double.valueOf(df.format(turnover)));
    }
    //球队-按赛季分组-各项数据场均
    public List<LinkedHashMap<String,Object>> getTeamAvgOfSeason(String teamId) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            MatchDataDao mdd = sqlSession.getMapper(MatchDataDao.class);
            ArrayList<LinkedHashMap<String,Object>> gameList = matchDao.getTeamGameCountBySeason(teamId);
            ArrayList<LinkedHashMap<String,Object>> dataList = mdd.getTeamSumBySeason(teamId);
            for (int i = 0; i <gameList.size() ; i++) {
                doAvgItem(dataList.get(i),Integer.valueOf(gameList.get(i).get("game").toString()));
            }
            return dataList;
        }
    }
    /**赛事基本信息 **/
    // 本场赛事-最佳数据
    ArrayList<LinkedHashMap<String,Object>> getBestDataOfMatch(String matchId) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String,Object>> bestData = new  ArrayList<>();
            LinkedHashMap<String,Object> score = matchDao.getMaxScoreHomeOfMatch("score",matchId);
            score.putAll(matchDao.getMaxScoreAwayOfMatch("score",matchId));
            bestData.add(score);
            LinkedHashMap<String,Object> bound = matchDao.getMaxBoundHomeOfMatch("bound",matchId);
            bound.putAll(matchDao.getMaxBoundAwayOfMatch("bound",matchId));
            bestData.add(bound);
            LinkedHashMap<String,Object> assist = matchDao.getMaxAssistHomeOfMatch("assist",matchId);
            assist.putAll(matchDao.getMaxAssistAwayOfMatch("assist",matchId));
            bestData.add(assist);
            return bestData;
        }
    }
    //本场赛事-双方数据对比
    ArrayList<TeamComparePojo> getCompareDataOfMatch(String matchId) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            LinkedHashMap<String,Object> home = matchDao.getTeamSumOfMatch("1",matchId);
            LinkedHashMap<String,Object> away = matchDao.getTeamSumOfMatch("0",matchId);
            ArrayList<TeamComparePojo> dataList = new ArrayList<>();
            dataList.add(new TeamComparePojo(home.get("得分"),"得分",away.get("得分")));
            dataList.add(new TeamComparePojo(home.get("助攻"),"助攻",away.get("助攻")));
            dataList.add(new TeamComparePojo(home.get("篮板"),"篮板",away.get("篮板")));
            dataList.add(new TeamComparePojo(home.get("抢断"),"抢断",away.get("抢断")));
            dataList.add(new TeamComparePojo(home.get("盖帽"),"盖帽",away.get("盖帽")));
            dataList.add(new TeamComparePojo(home.get("罚球"),"罚球",away.get("罚球")));
            dataList.add(new TeamComparePojo(home.get("犯规"),"犯规",away.get("犯规")));
            return  dataList;
        }
    }
    //public-赛事基本信息
    public LinkedHashMap getMatchInfo(String matchId) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            LinkedHashMap data = matchDao.getMatchInfo(matchId);
            Date date = (Date)data.get("date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            data.put("date",sdf.format(date));
            data.put("bestData",new ArrayList<>());
            data.put("countData",new ArrayList<>());
            data.put("homeData",new ArrayList<>());
            data.put("awayData",new ArrayList<>());
            if(Integer.valueOf(data.get("status").toString()) == 1) {
                data.put("bestData",getBestDataOfMatch(matchId));
                data.put("countData",getCompareDataOfMatch(matchId));
                data.put("homeData",matchDao.getTeamDataOfMatch(matchId,"1"));
                data.put("awayData",matchDao.getTeamDataOfMatch(matchId,"0"));
            }
            return data;
        }
    }
    /*赛季-所有赛事*/
    public LinkedHashMap<String,Object> getSeasonAllMatch(String season, Integer page,Integer pageSize){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            LinkedHashMap<String,Object> reMap = new LinkedHashMap<>();
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String, Object>> matchList = matchDao.getSeasonAllMatch(season,page,pageSize);
            reMap.put("count",matchList.size());
            ArrayList<LinkedHashMap<String, Object>> data = new ArrayList<>();
            for (LinkedHashMap<String,Object> item: matchList
                 ) {
                LinkedHashMap<String, Object> dataItem = new LinkedHashMap<>();
                dataItem.put("match", new MagMatchPojo(item.get("match_season"),
                        item.get("match_id"),item.get("match_date"),item.get("match_home"),
                        item.get("homeName"),item.get("match_away"),item.get("awayName"),
                        item.get("home_score"),item.get("away_score"),item.get("match_status")
                        )
                );
                ArrayList<MatchDataPojo> homeData = matchDao.getTeamDataOfMatch(item.get("match_id").toString(),"1");
                ArrayList<MatchDataPojo> awayData = matchDao.getTeamDataOfMatch(item.get("match_id").toString(),"0");
                dataItem.put("homeData",homeData);
                dataItem.put("awayData",awayData);
                data.add(dataItem);
            }
            reMap.put("data",data);
            return reMap;
        }
    }

    /**root-编辑赛事**/
    public MagMatchDto magEditMatch(MagMatchDto parse){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            int re  = matchDao.editMatch(parse);
            sqlSession.commit();
            if( re== 0 ) return null;
            return parse;
        }
    }
}
