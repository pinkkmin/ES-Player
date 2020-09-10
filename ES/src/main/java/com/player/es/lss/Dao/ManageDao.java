package com.player.es.lss.Dao;

import com.player.es.Domain.NoticeDomain;
import com.player.es.Domain.TeamDomain;
import com.player.es.Domain.UserDomain;
import com.player.es.lss.Domain.DTO.UserDto;

import java.util.List;
import java.util.Map;

public interface ManageDao {
    //    后台管理：修改用户信息
    int editUser(UserDto userDto);

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
    void pushNotice(NoticeDomain noticeDomain);

    //    后台管理：更新公告
    int editNotice(NoticeDomain noticeDomain);

    //    后台管理：查询公告
    List<NoticeDomain> queryNotice(Map<String, Object> map);
}
