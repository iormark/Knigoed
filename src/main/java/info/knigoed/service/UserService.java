package info.knigoed.service;

import info.knigoed.dao.UserDao;
import info.knigoed.dto.Message;
import info.knigoed.pojo.SignInForm;
import info.knigoed.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    //@Autowired
    //private RequestContext requestContext;
    @Autowired
    private UserDao userDao;

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User getByUserId(int userId) {
        return userDao.getByUserId(userId);
    }

    public String authUser(SignInForm user) {
        String error = null;
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            Md5Hash password = new Md5Hash(user.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), password.toHex());
            token.setRememberMe(true);

            try {
                currentUser.login(token);

                /*} catch (UnknownAccountException e) {
                result.put("error", "Мы не можем найти учетную запись с этим адресом электронной почты");
            } catch (IncorrectCredentialsException e) {
                result.put("error", "Мы не можем найти учетную запись с этими данными");
            } catch (LockedAccountException e) {
                result.put("error", "Заблокирован");*/
            } catch (AuthenticationException e) {
                error = "Мы не можем найти учетную запись с этим адресом электронной почты или паролем";
            }
        }
        return error;
    }


    public String setUser(User user) {
        String error = null;
        Subject currentUser = SecurityUtils.getSubject();
        LOG.debug("setUser isRemembered {}", currentUser.isRemembered());

        if (!currentUser.isAuthenticated()) {
            User byUser = userDao.getByEmail(user.getEmail());

            if (byUser == null) {
                Md5Hash emailHash = new Md5Hash(user.getEmail() + user.getPassword());
                // Create User
                userDao.setUser(user.getName(), user.getEmail(), user.getPassword(), emailHash.toHex());

                UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
                token.setRememberMe(true);
                try {
                    currentUser.login(token);
                } catch (AuthenticationException e) {
                    error = Message.MSG_ERROR_DEFAULT;
                }
            } else
                error = "Учетная запись с этим адресом электронной почты уже существует";
        }
        return error;
    }
}
