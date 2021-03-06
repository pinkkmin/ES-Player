package com.player.es.lss.Dao;

import com.player.es.Domain.NoticeDomain;
import com.player.es.Domain.TeamDomain;
import com.player.es.Domain.UserDomain;
import com.player.es.lss.Domain.DTO.UserDto;
import net.sf.saxon.expr.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ManageDao {
    //    后台管理：修改用户信息
    boolean editUser(Map<String,Object> map);

    //    后台管理：查询用户信息
    List<UserDomain> queryUser(Map<String, Object> map);

    //    后台管理：通过teamId得到teamName
    String getTeamName(String teamId);

    //    后台管理：新建球队
    int addTeam(TeamDomain teamDomain);

    //    获取最后一条team数据的id
    String getLastTeamId();

    //    后台管理：获取指定球队球队信息
    TeamDomain getActualTeam(String teamId);

    //    后台管理：删除指定公告
    int deleteActualNotice(String noticeId);

    //    后台管理：发布公告
    //void pushNotice(NoticeDomain noticeDomain);

    //    后台管理：更新公告
    int editNotice(NoticeDomain noticeDomain);

    //    后台管理：查询公告-返回一部分
    List<NoticeDomain> queryNotice(Map<String, Object> map);

//    后台管理：根据id获取用户信息
    UserDomain getUserDomainByUserId(String userId);

//    后台管理：获取查询到的公告的总数
    int getQueryNoticeNum(Map<String,Object> map);

//    后台管理：获取查询到的用户的总数
    int getQueryUserNum(Map<String,Object> map);

//    后台管理：查询service通过teamId
    List<Object> getServiceByTeamId(Map<String,Object> map);

//    后台管理：查询service通过teamId 的总量
    int getServiceByTeamIdNum(Map<String,Object> map);
    String isExistService(String serviceId);
//创建效力记录
    int createService(Map<String,Object> map);
//    后台管理：查询service
    List<Object> queryService(Map<String,Object> map);

//    后台管理：获得查询到的service的总数
    int getQueryServiceNum(Map<String,Object> map);
}
