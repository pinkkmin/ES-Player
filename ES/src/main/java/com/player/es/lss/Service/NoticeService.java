package com.player.es.lss.Service;

import cn.hutool.core.date.DateTime;
import com.player.es.Domain.NoticeDomain;
import com.player.es.lss.Dao.NoticeDao;
import com.player.es.Config.MybatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class NoticeService {
    public int createNotice(NoticeDomain noticeDomain){
        try(SqlSession sqlSession = MybatisConfig.getSqlSession()) {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            String noticeId = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
            while(noticeDao.isExistId(noticeId)!=null){
                noticeId = UUID.randomUUID().toString().replace("-", "").substring(0, 9);
            }
            noticeDomain.setNoticeId(noticeId);
            //System.out.println(noticeDomain);
            int status = noticeDao.createNotice(noticeDomain);
            sqlSession.commit();
            sqlSession.close();
            return status;
        }
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
