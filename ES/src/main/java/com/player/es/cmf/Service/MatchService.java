package com.player.es.cmf.Service;

import com.player.es.Utils.FileUtils;
import com.player.es.Utils.GlobalConstDataUtils;
import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Dao.MatchDataDao;
import com.player.es.cmf.Dao.PlayerDao;
import com.player.es.cmf.Dao.TeamDao;
import com.player.es.cmf.Domain.Dto.MagMatchDto;
import com.player.es.cmf.Domain.Dto.MatchDataDto;
import com.player.es.cmf.Domain.Dto.QueryMatchDto;
import com.player.es.cmf.Domain.POJO.MatchDataPojo;
import com.player.es.cmf.Domain.POJO.TeamComparePojo;
import com.player.es.Config.MybatisConfig;
import com.player.es.cmf.Domain.POJO.MagMatchPojo;
import com.player.es.lss.Dao.GlobalDao;
import org.apache.commons.collections.ArrayStack;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MatchService {
    private GlobalConstDataUtils globalConstData;
    private  DecimalFormat df = new DecimalFormat("#.0");

    public MatchService(){
        globalConstData = new GlobalConstDataUtils();
    }
    public LinkedHashMap<String,Object> getCurSeason(){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            GlobalDao globalDao = sqlSession.getMapper(GlobalDao.class);
            LinkedHashMap<String,Object> res = new LinkedHashMap<>();
            res.put("season",globalDao.getSetting("season"));
            return res;
           // MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
          // return matchDao.getCurrSeason();
        }
    }
    public LinkedHashMap getTeamSort(String teamId, String season) {
        List<LinkedHashMap> team = getALLTeamSort(season);
        for (LinkedHashMap item : team
        ) {
            if (teamId.equals(item.get("teamId"))) return item;
        }
        return null;
    }
    public LinkedHashMap getTeamInfoBySort(String teamId, String season) {
        LinkedHashMap team = getTeamSort(teamId, season);
        team.put("season",season);
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            team.putAll(teamDao.getTeamBaseInfo(teamId));
        }
        return team;
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
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);ArrayList<LinkedHashMap<String,Object>> bestData = new  ArrayList<>();

            LinkedHashMap<String,Object> score = matchDao.getMaxScoreHomeOfMatch("score",matchId);
            score.putAll(matchDao.getMaxScoreAwayOfMatch("score",matchId));
            score.put("index","得分");
            bestData.add(score);
            LinkedHashMap<String,Object> bound = matchDao.getMaxBoundHomeOfMatch("bound",matchId);
            bound.putAll(matchDao.getMaxBoundAwayOfMatch("bound",matchId));
            bound.put("index","篮板");
            bestData.add(bound);
            LinkedHashMap<String,Object> assist = matchDao.getMaxAssistHomeOfMatch("assist",matchId);
            assist.putAll(matchDao.getMaxAssistAwayOfMatch("assist",matchId));
            assist.put("index","助攻");
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
        LinkedHashMap<String,Object> data  = new LinkedHashMap<>();
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String, Object>> matchList = matchDao.queryMatch(item);
            int count = matchDao.queryMatchCount(item);
           data.putAll(getMatchDataList(matchList));
           data.replace("count",count);
        }
        return data;
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

    /*****上传赛事记录文件*/
    public LinkedHashMap<String,Object> doUpdateMatchData(MultipartFile file, Map match){
        LinkedHashMap<String,Object> data = new LinkedHashMap<>();
        FileUtils fileUtils = new FileUtils();
        String fileName = file.getOriginalFilename();
        String lastName = fileName.substring(fileName.lastIndexOf(".")+1);
        LinkedHashMap<String,Object> fileData ;
        if(lastName.equals("csv")) {
            fileData =  fileUtils.doMatchDataByCSV(file);
        }
        else if(lastName.equals("xls")){
            fileData = fileUtils.doMatchDataByXLS(file);
        }
        else {
            data.put("code",402);
            data.put("message","文件格式不正确");
            return data;
        }
        if(fileData == null) {
            data.put("code",400);
            data.put("message","文件读取出错,请检查重试");
            return data;
        }
        if((Integer)fileData.get("code") == 250) {
            data.put("code",250);
            data.put("message","文件格式不符合要求,请重新检查个字段是否齐全");
            return data;
        }
        String homeId = (String)match.get("homeId");
        String awayId = (String)match.get("awayId");
        //System.out.println(fileData);
        ArrayList<MatchDataPojo> homePlayer = (ArrayList)fileData.get("home"),awayPlayer = (ArrayList)fileData.get("away");
        ArrayList<MatchDataPojo> home = new ArrayList<>(),away = new ArrayList<>();
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            for (MatchDataPojo item : homePlayer
            ) {
                LinkedHashMap<String, Object> player = playerDao.getPlayerByName(homeId,item.getName());
                if(player!=null){
                    item.setNumber((Integer)player.get("number"));
                    item.setPlayerId((String)player.get("playerId"));
                    item.setName((String)player.get("name"));
                home.add(item);
                }
            }
            for (MatchDataPojo item : awayPlayer
            ) {
                LinkedHashMap<String, Object> player = playerDao.getPlayerByName(awayId,item.getName());
                if(player != null){
                    item.setNumber((Integer)player.get("number"));
                    item.setPlayerId((String)player.get("playerId"));
                    item.setName((String)player.get("name"));
                    away.add(item);
                }
            }
        }
        data.put("code",200);
        data.put("massage","读取文件成功");
        data.put("home",home);
        data.put("away",away);
        return  data;
    }
    public ArrayList<String> getSeasonList() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
        MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
       return  matchDao.getSeasonList();
        }
    }

    /**按日期每日比赛**/
    public ResponseUnit getMatchByDay(String season, String month) {
        ResponseUnit data = new ResponseUnit();
        ArrayList<LinkedHashMap<String, Object>> res = new ArrayList<>();
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<String> dayList = matchDao.getDayListByMonth(season, month);
            if (dayList.size() == 0) {
                data.setMessage("该月份没有比赛,显示最近比赛");
                dayList = matchDao.getDayByLastMonth(season);
            } else {
                data.setMessage("当月的比赛已显示");
            }
            //    System.out.println(dayList);
            for (String gameDay : dayList
            ) {
                LinkedHashMap<String, Object> item = new LinkedHashMap<>();
                item.put("day", gameDay);
                item.put("data", matchDao.getMatchByDay(season, gameDay));
                res.add(item);
            }
        }
        data.setCode(200);
        data.setData(res);
        return data;
    }
    void initMatchDataList(ArrayList<LinkedHashMap<String,Object>> data){
        for (LinkedHashMap<String,Object> res: data
             ) {
            res.put("score",0);
            res.put("assist",0);
            res.put("block",0);
            res.put("foul",0);
            res.put("free",0);
            res.put("bound",0);
            res.put("steal",0);
            res.put("turnOver",0);
            res.put("time",0);
            res.put("join",true); // 是否上场
        }
    }
    public LinkedHashMap<String,Object> queryNoDataMatch(Map<String,Object> item){
        LinkedHashMap<String,Object> res  = new LinkedHashMap<>();
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            res.put("count", matchDao.countNoDataMatch(item));
            ArrayList<LinkedHashMap<String,Object>> dataList = new ArrayList<>();
            ArrayList<LinkedHashMap<String,Object>> matchList = matchDao.queryNoDataMatch(item);
            for (LinkedHashMap<String,Object> match: matchList
                 ) {
                String homeId = (String)match.get("home"),awayId = (String)match.get("away");
                ArrayList<LinkedHashMap<String,Object>> homeData = matchDao.getPlayerByTeam(homeId);
                ArrayList<LinkedHashMap<String,Object>> awayData = matchDao.getPlayerByTeam(awayId);
              //  System.out.println(homeData);
                initMatchDataList(homeData);
                initMatchDataList(awayData);
                LinkedHashMap<String,Object> data = new LinkedHashMap<>();
                data.put("match",match);
                data.put("awayData",awayData);
                data.put("homeData",homeData);
                dataList.add(data);
            }
            /// 获取 主客的球员 设置数据为全0
            // 编写集体更新 接口
            res.put("data", dataList);
        }
        return res;
    }
    public  int insertMatchData(String matchId,ArrayList<LinkedHashMap<String,Object>> homeData,ArrayList<LinkedHashMap<String,Object>> awayData){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
            for (LinkedHashMap<String,Object> item: homeData
                 ) {
                boolean join = (boolean)item.get("join");
                if(join) {
                    item.put("isHome", 1);
                    item.put("matchId", matchId);
                    matchDataDao.insertMatchData(item);
                }
            }
            for (LinkedHashMap<String,Object> item: awayData
            ) {
                boolean join = (boolean)item.get("join");
                if(join) {
                    item.put("isHome", 0);
                    item.put("matchId", matchId);
                    matchDataDao.insertMatchData(item);
                }
            }
            int homeScore = matchDataDao.sumOfMatch(matchId,1);
            int awayScore = matchDataDao.sumOfMatch(matchId,0);
            matchDataDao.updateMatchScore(matchId,homeScore,awayScore);
            sqlSession.commit();
        }

        return 1;
    }

    public int insertMatch(Map<String,Object> map) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
            while (matchDao.isExist(id) != null) {
                id = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
            }
            map.put("matchId",id);
            SimpleDateFormat smpd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = smpd.parse(map.get("date").toString());
            map.put("date",date);

           int status =  matchDao.insertMatch(map);
            sqlSession.commit();
            return status;
        } catch (ParseException e) {
            e.printStackTrace();
           return 0;
        }
    }
}
