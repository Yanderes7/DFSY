package cn.synu.relam;

import cn.synu.domain.User;
import cn.synu.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName LoginRelam
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/2/3 20:42
 * @Version 1.0
 **/
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //鉴权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        if(user.getIdentity() == 1){
            //管理员
            List<Long> PermissionIds = userService.selPermission_id(Long.valueOf(1));
            List<String> currentUser_permission_admin = userService.selPermission(PermissionIds);
            System.err.println(currentUser_permission_admin);
            simpleAuthorizationInfo.addStringPermissions(currentUser_permission_admin);
        }else if(user.getIdentity() == 2){
            //学生
            List<Long> PermissionIds = userService.selPermission_id(Long.valueOf(2));
            List<String> currentUser_permission_student = userService.selPermission(PermissionIds);
            simpleAuthorizationInfo.addStringPermissions(currentUser_permission_student);
        }else if(user.getIdentity() == 3){
            //教师
            List<Long> PermissionIds = userService.selPermission_id(Long.valueOf(3));
            List<String> currentUser_permission_student = userService.selPermission(PermissionIds);
            simpleAuthorizationInfo.addStringPermissions(currentUser_permission_student);
        }
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.selectByUsername(username);
        if(user == null){
            return null;
        }
        //共享当前登录用户的具体信息
        userService.loginByUser(user);
        return new SimpleAuthenticationInfo(user, user.getPassword() ,getName());
    }
}
