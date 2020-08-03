package com.player.es.Controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.player.es.Config.MybatisConfig;
import com.player.es.Dao.UserDao;
import com.player.es.Domain.LoginDto;
import com.player.es.Domain.UserDomain;
import com.player.es.Service.UserService;
import com.player.es.Utils.JwtUtils;
import com.player.es.Utils.ResponseUnit;
import org.apache.ibatis.session.SqlSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

            return ResponseUnit.succ(MapUtil.builder()
                    .put("id", user.getUserID())
                    .put("username", user.getUserName())
                    .put("role", user.getRole())
                    .put("email", user.getEmail())
                    .map()
            );
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
    @GetMapping("/test")
    public ResponseUnit test () {
        return ResponseUnit.succ( MapUtil.builder()
                        .put("id", userService.test())
                        .put("user",userService.test())
                        .map()
                   );
    }
}
