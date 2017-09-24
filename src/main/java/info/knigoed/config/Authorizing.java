package info.knigoed.config;

import info.knigoed.pojo.User;
import info.knigoed.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

public class Authorizing extends AuthorizingRealm {
    private static final Logger LOG = LoggerFactory.getLogger(Authorizing.class);

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOG.info("Authorization {}", principals);
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRole().name());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException, EmptyResultDataAccessException {

        LOG.info("Authentication {}", token);
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user;

        try {
            user = userService.getByEmail(usernamePasswordToken.getUsername());
        } catch (EmptyResultDataAccessException e) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (user.getPassword().equals(new String(usernamePasswordToken.getPassword())))
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        else
            throw new AuthenticationException("Invalid username or password");
    }
}