package info.knigoed.service;

import info.knigoed.config.RequestContext;
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

    @Autowired
    private RequestContext requestContext;
    @Autowired
    private UserDao userDao;

    public User getByEmail(String username) {
        return userDao.getByEmail(username);
    }

    public Message authenticationUser(SignInForm user) {
        Message message = new Message();
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            Md5Hash password = new Md5Hash(user.getPassword().toLowerCase());
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
                message.setError("Мы не можем найти учетную запись с этим адресом электронной почты или паролем");
            }
        }

        return message;
    }


    public Message setUser(String name, String email, String password) {
        Message message = new Message();
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            User user = userDao.getByEmail(email);

            if (user == null) {
                Md5Hash emailHash = new Md5Hash(email.trim().toLowerCase());
                userDao.setUser(name, email, password, emailHash.toHex());
                UsernamePasswordToken token = new UsernamePasswordToken(email, password);
                token.setRememberMe(true);
                try {
                    currentUser.login(token);
                } catch (AuthenticationException e) {
                    message.setError(Message.MSG_ERROR_DEFAULT);
                }
            } else
                message.setError("Учетная запись с этим адресом электронной почты уже существует");
        }
        return message;
    }
}
