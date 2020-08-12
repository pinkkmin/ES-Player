package com.player.es.Controller;
/**
 * @2020/8/4 修改返回jwt
 * */
import cn.hutool.core.map.MapUtil;
import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.UserDao;
import com.player.es.Domain.LoginDto;
import com.player.es.Domain.UserDomain;
import com.player.es.Service.UserService;
import com.player.es.Utils.JwtUtils;
import com.player.es.Utils.ResponseUnit;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @RequestMapping("/login")
    public ResponseUnit login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
            UserDomain user = userService.getByUserName(loginDto.getUsername());
            //throws IllegalArgumentException
            Assert.notNull(user, "用户不存在");
       ///添加加密密码
        if(!user.getPasswd().equals(loginDto.getPassword())){
            return ResponseUnit.fail("用户名或密码不正确");
        }
        /*密码校验---->成功----->生成jwt*/
        String jwt = jwtUtils.generateToken(user.getUserID());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        Map test = new HashedMap() ;
        test.put("Authorization", jwt);
        test.put("id", user.getUserID());
        test.put("username", user.getUserName());
        test.put("role", user.getRole());
        test.put("email", user.getEmail());
        return ResponseUnit.succ(test);
    }
    @RequiresAuthentication
    @GetMapping("/index")
    public ResponseUnit index () {
        try (SqlSession sqlSession = MybatisConfig.getSqlSession()) {

            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<UserDomain> test = userDao.select();
            UserDomain user = test.get(0);
            user.setUserName("你好");
            return ResponseUnit.succ(user);
        }
    }
//  -------------------------------------------接口1：我的信息获取---------------------------------------
    @RequestMapping("/user/info")
    public ResponseUnit myInformation(@RequestBody Map<String,String> map){
        UserDomain user = userService.getByUserID(map.get("userId"));
//        System.out.println(userId);
        Map information = new HashMap();
        information.put("role", user.getRole());
        information.put("id",user.getUserID());
        information.put("email",user.getEmail());
        information.put("username",user.getUserName());
        return ResponseUnit.succ(information);
    }
//  -------------------------------------------接口1：我的信息获取---------------------------------------
//-------------------------------------------接口2：修改密码---------------------------------------
    @RequestMapping("/user/password")
    public ResponseUnit password_change(@RequestBody Map<String,String> map){
        boolean work = userService.passwordChange(map.get("userId"),map.get("password"),map.get("newPasswd"));
        if(work){
            return ResponseUnit.succ("");
        }
        else
            return ResponseUnit.fail("原密码不正确");
    }
//-------------------------------------------接口2：修改密码---------------------------------------

    @GetMapping("/test")
    public ResponseUnit test () {
        return ResponseUnit.succ( MapUtil.builder()
                        .put("id", userService.test())
                        .put("user",userService.test())
                        .map()
                   );
    }
}
