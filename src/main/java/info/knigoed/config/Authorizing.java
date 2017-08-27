package info.knigoed.config;

import info.knigoed.manager.UserManager;
import info.knigoed.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class Authorizing extends AuthorizingRealm {

    @Autowired
    private UserManager userManager;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("AuthorizationInfo");
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRole().name());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("AuthenticationInfo");

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user = userManager.getByUsername(usernamePasswordToken.getUsername());
        if (user != null && user.getPassword().equals(new String(usernamePasswordToken.getPassword()))) {
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        } else {
            throw new AuthenticationException("Invalid username/password combination!");
        }

    }
}