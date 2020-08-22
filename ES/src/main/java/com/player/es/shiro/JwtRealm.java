package com.player.es.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.player.es.Utils.JwtUtils;
import com.player.es.Domain.UserDomain;
import com.player.es.lss.Service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     * 这个token就是从过滤器中传入的jwtToken
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        //使用token认证
        UserDomain user = userService.getByUserID(userId);
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }
        JwtProfile profile = new JwtProfile();

        /**把UserDomain的一些属性返回
         通过反射将一个UserDomain的值赋值个AccountProfile对象（前提是对象中属性的名字相同）
         */
        BeanUtil.copyProperties(user, profile);
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
