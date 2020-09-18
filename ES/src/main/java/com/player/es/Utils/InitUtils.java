
package com.player.es.Utils;

import com.player.es.cmf.Dao.MatchDao;
import com.player.es.cmf.Dao.MatchDataDao;
import com.player.es.cmf.Dao.PlayerDao;
import com.player.es.Domain.MatchDomain;
import com.player.es.Domain.PlayerDomain;
import com.player.es.Config.MybatisConfig;
import com.player.es.Domain.MatchDataDomain;
import com.player.es.cmf.Dao.TeamDao;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class InitUtils {
    public BufferedReader readFile(String file) {
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "GB2312");
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //球队id列表
    public  LinkedHashMap<String,String> getTeamList() {
        String path = new String("C:\\Users\\HP\\Desktop\\es\\team.csv");
        LinkedHashMap<String,String> data = new LinkedHashMap<>();
        try {
            BufferedReader br = readFile(path);
            br.readLine();
            String player = br.readLine();
            String[] info = player.split(",");
            while(!info[0].equals("end")){
                data.put(info[3],info[4]);
                info = br.readLine().split(",");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return data;
    }
    //追加-录入外援球员信息
    public void addPlayerList(){
        String path = new String("C:\\Users\\HP\\Desktop\\es\\player\\addPlayer.csv");
        try {
            BufferedReader br  = readFile(path);
            br.readLine();
            String player = br.readLine();
            String[] pl = player.split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            while(!pl[0].equals("end")) {
                String pid = pl[0];
                String tid = pl[1];
                String name = pl[2];
                int num = Integer.valueOf(pl[3]);
                String position = pl[4];
                Date birth = dateFormat.parse(pl[5]);
                String height = pl[6];
                String weight = pl[7];
                int logo = 0;
                if(position.equals("SG") && Integer.valueOf(height)%2==0) position  ="PG";
                if(position.equals("SF") && Integer.valueOf(height)%2==0) position  ="PF";
                PlayerDomain pd = new PlayerDomain(pid,tid,name,num,birth,height,weight,position,logo);
                System.out.println(pd);
                try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                    PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
                    playerDao.initUpdatePlayer(pd);
                }
                player = br.readLine();
                pl = player.split(",");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //录入各球队-球员信息
    public void initPlayerList() {
        try {
            String path = new String("C:\\Users\\HP\\Desktop\\es\\team");
            File fileList = new File(path);
            String nameList[] = fileList.list();
            for (String fileName: nameList
                 ) {
                System.out.println(fileName);
                System.out.println("************************************************");
            BufferedReader br  = readFile(path+"\\"+fileName);
            br.readLine();
            String player = br.readLine();
                String[] pl = player.split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            while(!pl[0].equals("end")) {
                String pid = pl[0];
                String tid = pl[1];
                String name = pl[3];
                int num = Integer.valueOf(pl[4]);
                Date birth = dateFormat.parse(pl[6]);
                String height = pl[7];
                String weight = pl[8];
                String position = pl[5];
                int logo = 1;
                if(position.equals("SG") && Integer.valueOf(height)%2==0) position  ="PG";
                if(position.equals("SF") && Integer.valueOf(height)%2==0) position  ="PF";
                if(pl.length==9)  logo = 0;
                PlayerDomain pd = new PlayerDomain(pid,tid,name,num,birth,height,weight,position,logo);
                System.out.println(pd);
              try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
                playerDao.initPlayerList(pd);
                }
                player = br.readLine();
                pl = player.split(",");
            }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    //初始化 赛事列表
    public void initMatchList(String season) {
        try {
            LinkedHashMap<String,String> teamList = getTeamList();
            String path = new String("C:\\Users\\HP\\Desktop\\es\\season\\"+season+".csv");
            BufferedReader br  = readFile(path);
            br.readLine();
            String ml[] = br.readLine().split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            while (!ml[0].equals("end")) {
                Date matchData = dateFormat.parse(ml[2]);
                String home = teamList.get(ml[3]), away = teamList.get(ml[5]);
                MatchDomain md = new MatchDomain(ml[0],home,away,season,matchData);
                System.out.println(md);
                try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                    MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
                    matchDao.initMatchList(md);
                }
                ml = br.readLine().split(",");
            }
            }catch(Exception e){
                    e.printStackTrace();
            }
    }
    // 初始化赛事记录列表
    public void initMatchDataList(String season) {
        try {
            String path = new String("C:\\Users\\HP\\Desktop\\es\\season\\"+season);
            File fileList = new File(path);
            String nameList[] = fileList.list();
            for (String fileName: nameList
            ) {
                System.out.println(fileName);
                System.out.println("*************************************");
                String ml[] = fileName.split(".csv");
                String matchId = ml[0];
                int isHome = 1;
                BufferedReader br = readFile(path+"\\"+fileName);
                br.readLine();
                String str[] = br.readLine().split(",");
                while (!str[0].equals("end")) {
                    if(!str[0].equals("medium")) {
                        String playerId = str[0];
                        int time = Integer.valueOf(str[3]);
                        int score = Integer.valueOf(str[4]);
                        int assist = Integer.valueOf(str[5]);
                        int bound = Integer.valueOf(str[6]);
                        int steal = Integer.valueOf(str[7]);
                        int block = Integer.valueOf(str[8]);
                        int turnOver = Integer.valueOf(str[9]);
                        int foul = Integer.valueOf(str[10]);
                        int free = Integer.valueOf(str[11]);
                        MatchDataDomain mdd = new MatchDataDomain(matchId, playerId, isHome, score,
                                bound, assist, time, block, steal, foul, turnOver, free);
                        System.out.println(mdd);
                    try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                        MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
//                        if (!isExistPlayer(mdd.getPlayerId())) {
//                            String name = str[2];
//                            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
//                            LinkedHashMap team = matchDao.getTeamId(matchId);
//                            String teamId = new String();
//                            if(isHome ==1) {
//                                teamId = (String)team.get("home");
//                            }
//                            else   teamId = (String)team.get("away");
//                            PlayerDomain pd = new PlayerDomain(playerId,teamId,name);
//                            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
//                            System.out.println("***add: " + pd);
//                            playerDao.initPlayerList(pd);
//                        }
                        matchDataDao.initMatchDataList(mdd);
                    }
                    }
                    else {
                        System.out.println("---------------------------------------------");
                        isHome = 0;
                    }
                    str = br.readLine().split(",");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //球员是否存在
    public boolean  isExistPlayer(String playerId) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
            if(playerDao.isExist(playerId) == null) return false;
           return true;
        }
    }
    //赛事是否存在
    public boolean  isExistMatch(String matchId) {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
            if(matchDao.isExist(matchId) == null) return false;
            return true;
        }
    }
    public  void writeService(){
        try {
            BufferedReader br = readFile("C:\\Users\\HP\\Desktop\\team.csv");
            String fi = "C:\\Users\\HP\\Desktop\\info.csv";
            File file = new File(fi);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
                String line = br.readLine();
                String itemList[] =line.split(",");
                while (line != null) {
                    String playerId = itemList[0];
                    String teamId = itemList[1];
                    LinkedHashMap<String,String> info = teamDao.getServiceDate(playerId,teamId);

                    String date = info.get("date_");
                    bw.write(playerId+','+teamId+','+date);
                   // System.out.println(playerId);
                    bw.newLine();
                    line = br.readLine();
                    if(line!=null)
                    itemList =line.split(",");
                }
            }
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeServiceR() {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            TeamDao teamDao = sqlSession.getMapper(TeamDao.class);
        try{
            BufferedReader br = readFile("C:\\Users\\HP\\Desktop\\info.csv");
            String line = br.readLine();
            String info[] = line.split(",");
            Integer id  = 100000;
            while (line!=null) {
                String playerId = info[0];
                String teamId = info[1];
                String date = info[2];
                if(teamDao.isExistService(playerId)==null) {
                    teamDao.insertService(String.valueOf(id),playerId,teamId,date,1);
                }
                else{
                    teamDao.insertService(String.valueOf(id),playerId,teamId,date,2);
                }
                id++;
                line = br.readLine();
                if(line==null) break;
                info = line.split(",");
            }
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        }
    }
}
