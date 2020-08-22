package com.player.es.cmf.Service;

import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Dao.TeamDao;
import com.player.es.cmf.Domain.POJO.MatchPojo;
import com.player.es.cmf.Domain.POJO.PieItemPojo;
import com.player.es.cmf.Domain.POJO.SortItemPojo;
import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeamService {
    public List<Map> getTeamList() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao team = sqlSession.getMapper(TeamDao.class);
            return team.getTeamList();
        }
    }
    // 获取饼图数据
    public Map getPieData(String teamId, String season) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {

        }

     return null;
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
            item.remove("score");
            item.remove("assist");
            item.remove("bound");
            item.remove("steal");
            item.remove("block");
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
    public List<HashMap> getBoundOfAllPlayer(String teamId, String season){
        List<HashMap> data = SortItemData(teamId, season,"bound");
        for (HashMap item :data
        ) {
            System.out.println(item);
        }
        return null;
    }
    /*积分榜*/
    public ArrayList<SortItemPojo> getAllTeamSort(String season)  {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            ArrayList<LinkedHashMap>  sortList = matchDao.getAllTeamSort(season);
            ArrayList<SortItemPojo> data = new ArrayList<>();
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
                item.put("month",curYear+"-"+curMonth);
                item.put("data",matchItem);
                data.add(item);
            }
           return data;
        }
    }
}
