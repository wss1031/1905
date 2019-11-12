package com.jk.shiro;
import com.jk.model.UserModel;

import com.jk.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * author：wdd
 * create time:2019/10/12
 * email：
 * tel：
 */
public class MyRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从数据库获取当前用户所拥有的权限：权限表、角色权限关联表、用户角色关联表
        UserModel user = (UserModel) principalCollection.getPrimaryPrincipal();
        UserService userService = SpringBeanFactoryUtils.getBean("userService",UserService.class);
        List<String> powerList = userService.queryPowerByUserid(user.getUserId());

        //从数据库获取用户所用户的角色：角色表、用户角色管理表
        List<String> roleList = new ArrayList<>();
        roleList.add("superadmin");

        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        sai.addStringPermissions(powerList);
        sai.addRoles(roleList);
        return sai;
    }

    //认证
  @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户登录的用户名
        String username = (String) authenticationToken.getPrincipal();
        //通过工具类获取bookService
        UserService userService = SpringBeanFactoryUtils.getBean("userService", UserService.class);
        //从数据库获取用户信息
        UserModel user = userService.findUserByName(username);
        if(user==null){
            return null;
        }
        //用户名(存入session)、密码、当前类名
        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user,user.getUserpwd(),this.getName());
        return sai;
    }

}
