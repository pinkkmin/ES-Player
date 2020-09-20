package com.player.es.lss.Controller;
/**
 * @2020/8/4 修改返回jwt
 * */
import com.player.es.Utils.ResponseUnit;
import com.player.es.Domain.LoginDto;
import com.player.es.Domain.UserDomain;
import com.player.es.lss.Service.UserService;
import com.player.es.Utils.JwtUtils;
import com.player.es.shiro.JwtToken;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.map.HashedMap;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
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
//  -------------------------------------------接口1：我的信息获取---------------------------------------
    ///PS：cmf 修改了此部分, 获取用户id用于登陆
    @RequiresAuthentication
    @GetMapping("/api/user/info")
    public ResponseUnit myInformation(@RequestHeader ("Authorization") String jwt){
        Claims claim = jwtUtils.getClaimByToken(jwt);
        //获取用户Id
        String userId = claim.getSubject();
        return ResponseUnit.succ(200,"获取成功",userService.getUserInformation(userId));
    }
//-------------------------------------------接口2：修改密码---------------------------------------
    @RequestMapping("/api/user/password")
    public ResponseUnit password_change(@RequestBody Map<String,String> map){
        boolean work = userService.passwordChange(map.get("userId"),map.get("password"),map.get("newPasswd"));
        if(work){
            return ResponseUnit.succ("");
        }
        else
            return ResponseUnit.fail("原密码不正确");
    }
//    -------------------------------------------接口2：修改密码---------------------------------------
//    -------------------------------------------接口3：修改邮箱-----------------------------------------
    @RequestMapping("/api/user/email")
    public ResponseUnit email_change(@RequestBody HashMap<String, String> hashMap){
        boolean work = userService.emailChange(hashMap.get("userId"), hashMap.get("password"), hashMap.get("email"));
        if(work){
            return ResponseUnit.succ("");
        }
        else{
            return ResponseUnit.fail("修改失败");
        }
    }
//    -------------------------------------------接口3：修改邮箱-----------------------------------------
}
