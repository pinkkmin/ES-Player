package com.player.es.cmf.Service;

import com.player.es.Utils.EmailUtils;
import com.player.es.Utils.GlobalConstDataUtils;
import com.player.es.Utils.ResponseUnit;
import com.player.es.cmf.Dao.*;
import com.player.es.cmf.Domain.POJO.MatchPojo;
import com.player.es.cmf.Domain.POJO.PieItemPojo;
import com.player.es.cmf.Domain.POJO.SortItemPojo;
import com.player.es.Config.MybatisConfig;
import com.player.es.cmf.Domain.POJO.TeamComparePojo;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeamService {

    //utils
    private GlobalConstDataUtils globalConstData;
    private DecimalFormat df = new DecimalFormat("#.0");
    EmailUtils emailUtils = new EmailUtils();

    public TeamService() {
        globalConstData = new GlobalConstDataUtils();
    }
    public List<Map> getTeamList() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao team = sqlSession.getMapper(TeamDao.class);
            return team.getTeamList();
        }
    }
    public List<PieItemPojo> getPieItemList(List<HashMap> data) {
        List pie = new ArrayList<PieItemPojo>();
        for (HashMap item: data
             ) {
            Double value = Double.valueOf(item.get("data").toString());
            pie.add(new PieItemPojo(item.get("name").toString(),value));
        }
        Collections.sort(pie, (Comparator<PieItemPojo>) (lhs, rhs) -> lhs.getValue().compareTo(rhs.getValue()));
        return pie;
    }
    public List<HashMap> SortItemData(String teamId, String season,String item) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao team = sqlSession.getMapper(TeamDao.class);
            List<HashMap> data = team.getPlayerAvgData(teamId, season);
            Collections.sort(data, (Comparator<HashMap>) (lhs, rhs) -> {
                BigDecimal lhsValue = (BigDecimal) lhs.get(item);
                BigDecimal rhsValue = (BigDecimal) rhs.get(item);
                return rhsValue.compareTo(lhsValue);
            });
            return data;
        }
    }
    // 获取-某球队-所有球员-排序-某数据项-得分
    public void  removeItem(List<HashMap> itemList,String itemName) {
        for (HashMap item: itemList
             ) {
            BigDecimal data = (BigDecimal) item.get(itemName);
            for (String name: globalConstData.getEn5NameList()  ) {
                item.remove(name);
            }
            item.put("data", data);
        }
    }
    public HashMap getItemOfAllPlayer(String teamId, String season,String itemName){
        List<HashMap> scoreList = SortItemData(teamId, season,itemName);
        removeItem(scoreList,itemName);
        HashMap data = new HashMap(scoreList.get(0));
        List<PieItemPojo> tabPane = getPieItemList(scoreList);
        data.put("tabPane",tabPane);
        while(scoreList.size() > 4)
        scoreList.remove(scoreList.size()-1);
        data.put("tableData",scoreList);
        return data;
    }
    /*积分榜*/
    public LinkedList<SortItemPojo> getAllTeamSort(String season)  {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap>  sortList = matchDao.getAllTeamSort(season);
            LinkedList<SortItemPojo> data = new LinkedList<>();
            int mid = sortList.size()/2;
            for(int i = 0;i < mid;i++)  {
                data.add(new SortItemPojo(sortList.get(i),sortList.get(i+mid)));
            }
            return data;
        }
    }
    /*球队-赛季-所有赛事列表*/
    public ArrayList<LinkedHashMap<String,Object>> getTeamSeasonMatchList(String teamId, String season){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            ArrayList<MatchPojo> matchList =  teamDao.getTeamSeasonMatchList(teamId, season);
            ArrayList<LinkedHashMap<String,Object>> data = new ArrayList<>();
            int i = 0;
            while(i < matchList.size()){
                LinkedHashMap<String, Object> item = new LinkedHashMap<>();
                ArrayList<MatchPojo> matchItem = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(matchList.get(i).getDate());
                String ymd[] = date.split("-");
                int curMonth = Integer.valueOf(ymd[1]);
                int curYear = Integer.valueOf(ymd[0]);
                int count = 0;
                while(i<matchList.size()) {
                    String date_ = sdf.format(matchList.get(i).getDate());
                    String ymd_[] = date_.split("-");
                    int month = Integer.valueOf(ymd_[1]);
                    int year = Integer.valueOf(ymd_[0]);
                    if(month != curMonth || year != curYear) break;
                    else {
                        matchItem.add(matchList.get(i));
                        count++;
                    }
                    i++;
                }
                item.put("month",curYear+"年"+curMonth);
                item.put("data",matchItem);
                data.add(item);
            }
           return data;
        }
    }
    /**球队管理-API **/
    public int getLastMatchData(String teamId,ArrayList<LinkedHashMap<String,Object>> matchList,
                                 ArrayList<Integer> get,ArrayList<Integer> lost, ArrayList<String> date) {
        int win = 0;
        for (LinkedHashMap<String,Object> item:matchList
             ) {
            date.add(0,item.get("date").toString());
            if(teamId.equals(item.get("match_home").toString())) {
                get.add(0,Integer.valueOf(item.get("home_score").toString()));
                lost.add(0,Integer.valueOf(item.get("away_score").toString()));
            }
            else {
                get.add(0,Integer.valueOf(item.get("away_score").toString()));
                lost.add(0,Integer.valueOf(item.get("home_score").toString()));
            }
            if(get.get(0)>lost.get(0)) win++;
        }
        return win;
    }
    public LinkedHashMap<String,Object> getTeamSortInfo(String teamId, String season){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap> teamList = matchDao.getAllTeamSort(season);
            for (LinkedHashMap item : teamList
            ) {
                if (item.get("teamId").toString().equals(teamId)) {
                    item.remove("sort");
                        return new LinkedHashMap<>(item);
                }
            }
            return new LinkedHashMap<>();
        }
    }
    //***********************************
    public LinkedHashMap<String,Object> getMatchListData(String teamId,ArrayList<LinkedHashMap<String,Object>> matchList){
        LinkedHashMap<String,Object> data = new LinkedHashMap<>();
        ArrayList<Integer> getData = new ArrayList<>(), lostData = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        Integer win = getLastMatchData(teamId,matchList,getData,lostData,date);
        data.put("win",win);
        data.put("fail",matchList.size()-win);
        data.put("getData",getData);
        data.put("lostData",lostData);
        data.put("date",date);
        return data;
    }
    public LinkedHashMap<String,Object> getLastMatchList(String teamId,String season,ArrayList<LinkedHashMap<String,Object>> matchList){
        LinkedHashMap<String,Object> data = getTeamSortInfo(teamId,season);
        data.putAll(getMatchListData(teamId, matchList));
        return data;

    }
    public LinkedHashMap<String,Object> lastSevenMatch(String teamId,String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            ArrayList<LinkedHashMap<String,Object>> matchList = teamDao.lastSevenMatch(teamId);
            return getLastMatchList(teamId,season,matchList);
        }
    }
    public LinkedHashMap<String,Object> lastSeasonMatch(String teamId,String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            ArrayList<LinkedHashMap<String,Object>> matchList = teamDao.lastSeasonMatch(teamId,season);
            return getLastMatchList(teamId,season,matchList);
        }
    }
    public LinkedHashMap<String,Object> compareTeamMatch(String homeId,String awayId,String season) {
        LinkedHashMap<String,Object> data  = new LinkedHashMap<>();
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            ArrayList<LinkedHashMap<String,Object>> seasonMatch = teamDao.compareMatchByTeam(homeId,awayId,season);
            ArrayList<LinkedHashMap<String,Object>> passMatch = teamDao.compareMatch(homeId,awayId);
            data.put("season",getMatchListData(homeId,seasonMatch));
            data.put("allMatch",getMatchListData(homeId,passMatch));
        }
        return data;
    }

    /***compare team API**/
    ArrayList<TeamComparePojo> getCompareDataOfTeam(String homeId,String awayId,String season) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao mdd = sqlSession.getMapper(MatchDataDao.class);
            MatchDao md = sqlSession.getMapper(MatchDao.class);
            LinkedHashMap<String,Object> home = mdd.getTeamSumByCN(homeId,season);
            LinkedHashMap<String,Object> away =  mdd.getTeamSumByCN(awayId,season);
            ArrayList<TeamComparePojo> dataList = new ArrayList<>();
            int homeGame = md.getTeamGameCount(homeId,season), awayGame = md.getTeamGameCount(awayId,season);
            for (String name : globalConstData.getCnCmpNameList()
                 ) {
                dataList.add(new TeamComparePojo(home.get(name), name, away.get(name),homeGame,awayGame));
            }
            return dataList;
        }
    }
    public int getBestItemCount(ArrayList<TeamComparePojo> data) {
        int count = 0;
        for(TeamComparePojo item:data){
            if(item.getHomeValue()>item.getAwayValue()) count++;
        }
        return count;
    }
    /// API COMPARE TEAM
    public LinkedHashMap<String,Object> compareTeam(String homeId,String awayId,String season) {
        LinkedHashMap<String,Object> data = new LinkedHashMap<>(),baseLineData = new LinkedHashMap<>();
        LinkedHashMap<String,Object> home = getTeamSortInfo(homeId,season),away = getTeamSortInfo(awayId,season);
        LinkedHashMap radarData = new MatchDataService().getRadarData(homeId,season);
        ArrayList teamData = (ArrayList)radarData.get("data");
        teamData.add(new MatchDataService().getAvgItemOfTeam(awayId,season));
        ArrayList<TeamComparePojo> barData = getCompareDataOfTeam(homeId,awayId,season);
        int homeBest = getBestItemCount(barData);
        home.put("best",homeBest);
        away.put("best",barData.size()-homeBest);
        data.put("home",home);
        data.put("away",away);
        data.put("radarData",radarData);
        baseLineData.put("home",lastSevenMatch(homeId,season));
        baseLineData.put("away",lastSevenMatch(awayId,season));
        data.put("baseLineData",baseLineData);
        data.put("barData",barData);
        return data;
    }
    /***********************************API  compare player **************************************
     * 球员对比数据
     */
    public LinkedHashMap<String,Object> comparePlayer(String homeId,String awayId,String season){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            LinkedHashMap<String,Object> data = new LinkedHashMap<>(); //return
            LinkedHashMap<String,Object> home =  playerDao.getPlayerInfoOfTeam(homeId),away = playerDao.getPlayerInfoOfTeam(awayId);
            LinkedHashMap<String,Object> radarData = getRadarData(homeId,home.get("playerName").toString(),season);
            ArrayList<LinkedHashMap> radarDataList = (ArrayList)radarData.get("data");
            radarDataList.add(getRadarDataItem(awayId,away.get("playerName").toString(),season));
            LinkedHashMap<String,Double> homeAvg = playerDao.getSeasonAvg(homeId,season),awayAvg = playerDao.getSeasonAvg(awayId,season);
            ArrayList<TeamComparePojo> barData  = new MatchService().getCompareDataList(homeAvg, awayAvg);
            int best = getBestItemCount(barData);
            home.put("best",best);
            home.replace("position",getPositionByCN(home.get("position")));
            away.put("best",barData.size()-best);
            away.replace("position",getPositionByCN(away.get("position")));
            data.put("home",home);
            data.put("away",away);
            data.put("radarData",radarData);
            data.put("barData",barData);
            return data;
        }

    }
    //ArrayList 各项数据列表 as [21.9, 1.2, 5.5, 5.5, 0.8]
    public ArrayList<Double> getPlayerAvgItem(LinkedHashMap<String,Double> data){
        ArrayList<Double> re = new ArrayList<>();
        for ( String name : globalConstData.getCn5NameList()
             ) {
            if(data.get(name) ==null) re.add(0.0);
            else
            re.add(Double.valueOf(String.valueOf(data.get(name))));
        }
        return re;
    }
    //获取雷达图数据-data部分-子项
    public LinkedHashMap<String,Object> getRadarDataItem(String playerId,String playerName, String season) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            LinkedHashMap<String, Object> dataItem = new LinkedHashMap<>();
            LinkedHashMap<String, Double> avg = playerDao.getSeasonAvg(playerId, season);
            dataItem.put("value", getPlayerAvgItem(avg));
            dataItem.put("name", playerName);
            return dataItem;
        }
    }
    // 获取-雷达图-数据
    public LinkedHashMap getRadarData(String playerId,String playerName,String season) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDataDao mdd = sqlSession.getMapper(MatchDataDao.class);
            LinkedHashMap<String, Object> radarData = new LinkedHashMap<>();
            ArrayList<LinkedHashMap> data = new ArrayList<>();
            //put data
            data.add(getRadarDataItem(playerId,playerName,season));
            radarData.put("data",data);
            //put maxData
            radarData.put("maxData",mdd.getMaxItemOfPlayer(season));
            return radarData;
        }
    }
    //返回中文字符-position名称
    public String getPositionByCN(Object position) {
        if(position.toString().equals("C")) return "中锋";
        else if(position.toString().equals("SG")) return "得分后卫";
        else if(position.toString().equals("SF")) return "小前锋";
        else if(position.toString().equals("PF")) return "大前锋";
        else return "控球后卫";
    }

    /******************************API public player****************************************
     * description 获取球员界面的数据
     * @param playerId
     * @param season
     * @return
     */
    public LinkedHashMap<String,Object> getPublicPlayer(String playerId, String season) {
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            LinkedHashMap<String,Object> data = new LinkedHashMap<>();
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            data.putAll(playerDao.getPlayerInfoOfTeam(playerId));
            data.replace("position",getPositionByCN(data.get("position")));
            LinkedHashMap<String,Double> avg = playerDao.getSeasonAvg(playerId,season);
            data.put("score",avg.get("得分"));
            data.put("assist",avg.get("助攻"));
            data.put("bound",avg.get("篮板"));
            LinkedHashMap<String,Object> radarData = getRadarData(playerId,data.get("playerName").toString(),season);
            data.put("barData",getPlayerBarData(playerId,season));
            data.put("radarData",radarData);
            data.put("seasonData",playerDao.getPlayerSeasonData(playerId,season));
            data.put("careerData",playerDao.getPlayerAvgDataOfSeason(playerId));
            return data;
        }
    }
    // 获取-球员柱状图
    public LinkedHashMap<String,Object> getPlayerBarData(String playerId,String season) {
            LinkedHashMap<String, Object> barData = new LinkedHashMap();
            barData.put("title", "赛季数据对比");
            barData.put("min", 0);
            barData.put("max", 50);
            barData.put("data", getPlayerBarDataItem(playerId,season));
            return barData;

    }
    // 获取-柱状图-data部分-添加Item
    public ArrayList<ArrayList<Object>> getPlayerBarDataItem(String playerId,String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            ArrayList<ArrayList<Object>> data = new ArrayList<>();
            LinkedHashMap<String, Double> playerAvg = playerDao.getSeasonAvg(playerId, season);
            LinkedHashMap<String, Double> allPlayerAvg = playerDao.getAllPlayerSeasonAvg(season);
            ArrayList<Object> maxAvg = new ArrayList<>();
            maxAvg.add("最高");
            maxAvg.add(playerDao.getSeasonMaxAvgScore(season));
            maxAvg.add( playerDao.getSeasonMaxAvgAssist(season));
            maxAvg.add( playerDao.getSeasonMaxAvgBound(season));
            maxAvg.add( playerDao.getSeasonMaxAvgSteal(season));
            maxAvg.add( playerDao.getSeasonMaxAvgBlock(season));
            maxAvg.add(playerDao.getSeasonMaxAvgTurnOver(season));
            maxAvg.add( playerDao.getSeasonMaxAvgFoul(season));
            data.add(getArrayItemOfBarData());
            data.add(getArrayValueByCN("球员",playerAvg));
            data.add(maxAvg);
            data.add(getArrayValueByEN("平均",allPlayerAvg));
//            System.out.println(data);
            return data;
        }
    }
    //返回对应的item组成的List
    public ArrayList getArrayItemOfBarData(){
        ArrayList<Object> item = new ArrayList();
        item.add("item");
        for (String name : globalConstData.getCnNameList() ) {
            item.add(name);
        }
        return item;
    }
    //根据中文关键字 获取对应的值--->返回对应的值组成的List
    public ArrayList getArrayValueByCN(String itemName, LinkedHashMap<String, Double> value){
        ArrayList<Object> data = new ArrayList();
        data.add(itemName);
        for (String name : globalConstData.getCnNameList() ) {
            if(value.get(name) ==null) data.add(0.0);
            else
            data.add(Double.valueOf(String.valueOf(value.get(name))));
        }
        return data;
    }
    //根据英文关键字 获取对应的值--->返回对应的值组成的List
    public ArrayList getArrayValueByEN(String itemName, LinkedHashMap<String, Double> value){
        ArrayList<Object> data = new ArrayList();
        data.add(itemName);
        for (String name : globalConstData.getEnNameList() ) {
            if(value.get(name) ==null) data.add(0.0);
            else
            data.add(Double.valueOf(String.valueOf(value.get(name))));
        }
        return data;
    }
    ///////////
    /********API public/player/season******/
    public ArrayList<LinkedHashMap<String,Double>> getPlayerSeasonDataList(String playerId,String season){
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            ArrayList<LinkedHashMap<String,Double>> dataList= playerDao.getPlayerSeasonData(playerId,season);
           return dataList;
        }
    }
    ////////////
    /***API  team/analysis/season***
     * 球队赛季各项数据
     */
    public Double getDoubleData(Object data, Object count){
        Double reData = Double.valueOf(data.toString())/Double.valueOf(count.toString());
        return Double.valueOf(df.format(reData));
    }
    public LinkedHashMap<String,Object> getTeamSeasonAvgItem( int opt,ArrayList<LinkedHashMap<String,Object>> dataList,
                                                              ArrayList<LinkedHashMap<String,Object>> gameList){
        LinkedHashMap<String,Object> data = new LinkedHashMap<>();
        ArrayList<LinkedHashMap<String,Object>> seasonTable = new ArrayList<>();
        ArrayList<String> seasonList = new ArrayList<>();
        ArrayList<ArrayList<Double>> valueList = new ArrayList<>(7);
        for(int i = 0;i<7;i++) {
            valueList.add(new ArrayList<>());
        }
        ArrayList<String> nameList = globalConstData.getEnNameList();
        for (int i = 0;i<Math.min(dataList.size(),gameList.size());i++) {
            LinkedHashMap<String,Object> tableItem = new LinkedHashMap<>();
            tableItem.put("season",dataList.get(i).get("season").toString());
            seasonList.add(dataList.get(i).get("season").toString());
            for(int j = 0;j<7;j++) {
                String name = nameList.get(j);
                long game = (Long)gameList.get(i).get("game");
                if(opt == 1) game *= 2;
                Double value = getDoubleData(dataList.get(i).get(name),game);
                tableItem.put(name,value);
                valueList.get(j).add(value);
            }
            seasonTable.add(tableItem);
        }
        if(opt == 1 ) {
            nameList = globalConstData.getAllTeamList();
        }
        for(int index = 0;index<7;index++) {
            LinkedHashMap<String,Object> item = new LinkedHashMap<>();
            item.put("name",new ArrayList(seasonList));
            item.put("data",valueList.get(index));
            data.put(nameList.get(index)+"Season",item);
        }
        if(opt==0) data.put("seasonTable",seasonTable);
        return data;
    }
    public LinkedHashMap<String,Object> getTeamSeasonAvgList(String teamId,String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            LinkedHashMap<String,Object> data = teamDao.getTeamInfoById(teamId);
            MatchDao matchDao =sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap<String,Object>> teamGameCount =  matchDao.getTeamGameCountList(teamId);
            ArrayList<LinkedHashMap<String,Object>> teamDataList =matchDao.getTeamSeasonSumList(teamId);
            ArrayList<LinkedHashMap<String,Object>> allTeamGameCount =  matchDao.getAllTeamGameCountList();
            ArrayList<LinkedHashMap<String,Object>> allTeamDataList = matchDao.getAllTeamSeasonSumList();

            data.putAll(getTeamSeasonAvgItem(0,teamDataList,teamGameCount));
           data.putAll(getTeamSeasonAvgItem(1,allTeamDataList,allTeamGameCount));
        return data;
        }

    }
    public ArrayList<LinkedHashMap<String,Object>> getPlayerArray(String teamId, String season) {
        ArrayList<LinkedHashMap<String,Object>> data = new ArrayList<>();
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            data = teamDao.playerArrayByTeam(teamId);
            SimpleDateFormat smd = new SimpleDateFormat("yyyy");
            int nowYear =   Integer.valueOf(smd.format(new Date()));
            for (LinkedHashMap<String,Object> item:data
                 ) {
                String playerId = (String)item.get("playerId");
                Double age = (Double)item.get("age");
                if(age.intValue() == nowYear) {
                    item.put("age","--");
                }
            //    System.out.println(playerId);
                LinkedHashMap<String,Object> avgData = playerDao.getSeasonAvgByEn(playerId,season);
               // System.out.println(avgData);
                item.putAll(avgData);
//               System.out.println(item);
            }
        }
        return data;
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    public ResponseUnit getKeyNumber(String email,int type) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            int keyNumber = (int) (Math.random() * (999999 - 100001)) + 100001;
            boolean sign = emailUtils.toSend(email,keyNumber,type);
            if(sign) {
                KeyDao keyDao = sqlSession.getMapper(KeyDao.class);
                keyDao.deleteKey(email);
                sqlSession.commit();
                keyDao.insertKey(email, keyNumber, new Date());
                sqlSession.commit();
                return new ResponseUnit(200,"验证码已发送,请注意查收",null);
            }
        }
        return new ResponseUnit(400,"验证码发送失败",null);
    }
    public ResponseUnit keyNumberRight(Map<String,String> map) {
        String email = map.get("email");
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            KeyDao keyDao = sqlSession.getMapper(KeyDao.class);
            LinkedHashMap<String, Object> res = keyDao.getKeyNumber(email);
            String resEmail = (String) res.get("email");
            if (null != resEmail) { // 用户存在
                Date keyTime = (Date) res.get("key_time");
                int keyNumber = (Integer) res.get("key_");
                int kn = Integer.valueOf(map.get("keyNumber"));
                Long start = keyTime.getTime() + 300900;
                Long now = new Date().getTime();
                if (keyNumber != kn) {
                    return new ResponseUnit(400, "验证码错误", null);
                }
                if (now <= start) {
                    keyDao.deleteKey(email);
                    return new ResponseUnit(200, "密码已重置", null);
                } else {
                    return new ResponseUnit(400, "验证码失效", null);
                }
            } else {// 用户不存在
                return new ResponseUnit(400, "验证码已过期", null);
            }
        }
    }
    public ResponseUnit resetPwd(Map<String,String> map) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            ResponseUnit data =  keyNumberRight(map);
            return data;
        }
    }
    public ResponseUnit register(Map<String,String> map) {
            try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                ResponseUnit data =  keyNumberRight(map);
                if(data.getCode()==200){
                    System.out.println("注册成功");
                }
                return data;
            }
    }
}
