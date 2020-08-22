package com.player.es.Service;

import cn.hutool.core.date.DateTime;
import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.NoticeDao;
import com.player.es.Domain.NoticeDomain;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NoticeService {
    public List<NoticeDomain> getALL(){
        SqlSession sqlSession= MybatisConfig.getSqlSession();
        NoticeDao mapper = sqlSession.getMapper(NoticeDao.class);
        List<NoticeDomain> allNotice = mapper.test();
        sqlSession.close();
        return allNotice;
    }

    public int createNotice(NoticeDomain noticeDomain){
        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NoticeDao mapper = sqlSession.getMapper(NoticeDao.class);
        int status = mapper.createNotice(noticeDomain);
        sqlSession.commit();
        sqlSession.close();
        return status;
    }

    public int editNotice(HashMap<String,String> hashMap){
        SqlSession sqlSession = MybatisConfig.getSqlSession();
        NoticeDao mapper = sqlSession.getMapper(NoticeDao.class);
        hashMap.put("date",(new DateTime()).toString());
        int status = mapper.editNotice(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return status;
    }
}
