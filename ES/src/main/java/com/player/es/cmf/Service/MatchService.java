package com.player.es.cmf.Service;

import com.player.es.Utils.GlobalConstDataUtils;
import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Dao.MatchDataDao;
import com.player.es.cmf.Domain.Dto.MagMatchDto;
import com.player.es.cmf.Domain.Dto.QueryMatchDto;
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
    private GlobalConstDataUtils globalConstData;
    private  DecimalFormat df = new DecimalFormat("#.0");

    public MatchService(){
        globalConstData = new GlobalConstDataUtils();
    }

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
            List<LinkedHashMap> data = matchDao.getAllTeamSort(season);
            return data ;
        }
    }

    // 球队-按赛季分组-各项数据平均
    Double getDoubleByObject(Object value,int game){
        Double data = Double.valueOf(value.toString())/game;
      return  Double.valueOf(df.format(data));
    }
    void doAvgItem(HashMap<String,Object> data,int game) {
        for (String name:globalConstData.getEn8NameList()
             ) {
            Double value = getDoubleByObject(data.get(name),game);
            data.put(name,value);
        }
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
    public  ArrayList<TeamComparePojo> getCompareDataList( LinkedHashMap<String,Double> home, LinkedHashMap<String,Double> away){
        ArrayList<TeamComparePojo> dataList = new ArrayList<>();
        for (String name:globalConstData.getCnCmpNameList()
             ) {
            dataList.add(new TeamComparePojo(home.get(name),name,away.get(name)));
        }
        return  dataList;
    }
    ArrayList<TeamComparePojo> getCompareDataOfMatch(String matchId) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            LinkedHashMap<String,Double> home = matchDao.getTeamSumOfMatch("1",matchId);
            LinkedHashMap<String,Double> away = matchDao.getTeamSumOfMatch("0",matchId);
            return getCompareDataList(home,away);
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
    public LinkedHashMap<String, Object> getMatchDataList(ArrayList<LinkedHashMap<String, Object>> matchList ) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            LinkedHashMap<String,Object> reMap = new LinkedHashMap<>();
            reMap.put("count",matchList.size());
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String, Object>> data = new ArrayList<>();
            for (LinkedHashMap<String,Object> item: matchList
            ) {
                LinkedHashMap<String, Object> dataItem = new LinkedHashMap<>();
                dataItem.put("match", new MagMatchPojo(item.get("match_season"),
                                item.get("match_id"), item.get("match_date"), item.get("match_home"),
                                item.get("homeName"), item.get("match_away"), item.get("awayName"),
                                item.get("home_score"), item.get("away_score"), item.get("match_status")
                        )
                );
                ArrayList<MatchDataPojo> homeData = matchDao.getTeamDataOfMatch(item.get("match_id").toString(), "1");
                ArrayList<MatchDataPojo> awayData = matchDao.getTeamDataOfMatch(item.get("match_id").toString(), "0");
                dataItem.put("homeData", homeData);
                dataItem.put("awayData", awayData);
                data.add(dataItem);
            }
            reMap.put("data",data);
            return reMap;
        }
    }
    public LinkedHashMap<String,Object> getSeasonAllMatch(String season, Integer page,Integer pageSize){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String, Object>> matchList = matchDao.getSeasonAllMatch(season,page,pageSize);
            return getMatchDataList(matchList);
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
    /**查询-赛事**/
    public LinkedHashMap<String,Object> queryMatch(QueryMatchDto item){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            LinkedHashMap<String,Object> reMap = new LinkedHashMap<>();
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String, Object>> matchList = matchDao.queryMatch(item);
           return getMatchDataList(matchList);
        }
    }
    /**todayMatch**/
    public LinkedHashMap<String,Object> getTodayMatch(){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            ArrayList<LinkedHashMap<String, Object>> matchList;
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            matchList = matchDao.todayMatch();
            if (matchList.size() == 0) {
                matchList = matchDao.sevenMatchAfterToday();
                if (matchList.size() == 0) matchList = matchDao.sevenMatchBeforeToday();
            }
            data.put("count", matchList.size());
            data.put("data", matchList);
            return data;
        }
    }
}
