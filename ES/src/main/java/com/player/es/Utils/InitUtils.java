package com.player.es.Utils;

import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.MatchDao;
import com.player.es.Dao.MatchDataDao;
import com.player.es.Dao.PlayerDao;
import com.player.es.Domain.MatchDataDomain;
import com.player.es.Domain.MatchDomain;
import com.player.es.Domain.PlayerDomain;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitUtils {
    public BufferedReader readFile(String file) {
        try{
            String fileName = new String("C:\\Users\\HP\\Desktop\\data\\");
            fileName += file + ".csv";
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "GB2312");
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // 初始化球员列表
    public void initPlayerList() {
        try {
            BufferedReader br  = readFile("player");
            br.readLine();
            String player = br.readLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            while(player!=null) {
                String[] pl = player.split(",");
                String id = pl[0];
                String teamId_ = pl[1];
                String name= pl[2];
                Date birth = dateFormat.parse(pl[3]);
                String height = pl[4];
                String weight = pl[5];
                String position = pl[6];
                 if(position.equals("SG") && Integer.valueOf(height)%2==0) position  ="PG";
                if(position.equals("SF") && Integer.valueOf(height)%2==0) position  ="PF";
                int servicing = Integer.valueOf(pl[7]);
                PlayerDomain pd = new PlayerDomain(id,teamId_,name,birth,height,weight,position,servicing);
                try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                PlayerDao playerDao = sqlSession.getMapper(PlayerDao.class);
                playerDao.initPlayerList(pd);
                }
                player = br.readLine();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    //初始化 赛事列表
    public void initMatchList() {
        try {
            BufferedReader br  = readFile("match");
            br.readLine();
            br.readLine();
            String demo = br.readLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            while (demo != null) {
                String str[] = demo.split(",");
                String id =  str[0];
                String home = str[6];
                String away = str[7];
                String season = str[2];
                Date date = dateFormat.parse(str[1]);
                int status= Integer.valueOf(str[3]);
                int homeScore = Integer.valueOf(str[4]);
                int awayScore = Integer.valueOf(str[5]);
                MatchDomain md  =new MatchDomain(id, home, away, season,date, status, homeScore, awayScore);
                try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                    MatchDao matchDao = sqlSession.getMapper(MatchDao.class);
                    matchDao.initMatchList(md);
                }
                demo = br.readLine();
            }
            }catch(Exception e){
                    e.printStackTrace();
            }
    }
    // 初始化赛事记录列表
    public void initMatchDataList() {
        try {
            BufferedReader br  = readFile("matchdata");
            br.readLine();
            String demo = br.readLine();
            while (demo != null) {
                String str[] = demo.split(",");
                String matchId = str[0];
                String playerId = str[1];
                int isHome = Integer.valueOf(str[2]);
                int score = Integer.valueOf(str[3]);
                int bound = Integer.valueOf(str[4]);
                int assist = Integer.valueOf(str[5]);
                int matchTime =  Integer.valueOf(str[6]);
                int block = Integer.valueOf(str[7]);
                int steal = Integer.valueOf(str[8]);
                int foul = Integer.valueOf(str[9]);
                int turnOver = Integer.valueOf(str[10]);
                int free = Integer.valueOf(str[11]);
                MatchDataDomain mdd = new MatchDataDomain(matchId,playerId,isHome,score,
                        bound,assist,matchTime, block,steal,foul,turnOver,free);
                try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {
                    MatchDataDao matchDataDao = sqlSession.getMapper(MatchDataDao.class);
               if (isExistPlayer(mdd.getPlayerId()) && isExistMatch(mdd.getMatchId()))
               { System.out.println("info:    " + mdd.toString());
                    System.out.println(isExistPlayer(mdd.getPlayerId()) +" : "+isExistMatch(mdd.getMatchId()));
                  matchDataDao.initMatchDataList(mdd);
               }
                }
                demo = br.readLine();
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
}
