package com.player.es.lss.Service;

import cn.hutool.core.date.DateTime;
import com.player.es.Domain.NoticeDomain;
import com.player.es.lss.Dao.NoticeDao;
import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NoticeService {
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
