package info.knigoed.controller;

import info.knigoed.dao.Invoice;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private Invoice invoice;
    @Autowired
    private WebSecurityManager securityManager;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model) throws IOException {
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            System.out.println(currentUser.getPrincipal());
        }

        model.addAttribute("bundle", "signin");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = "application/json")
    public HashMap signinPost(@RequestParam("username") String username, @RequestParam("password") String password, Model model) throws IOException {
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        HashMap result = new HashMap();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(false);

            try {
                currentUser.login(token);
            } catch (UnknownAccountException e) {
                result.put("error","Нет такого пользователя");
            } catch (IncorrectCredentialsException e) {
                result.put("error","Не верный пароль");
            } catch (LockedAccountException e) {
                result.put("error","Нет такого юзера");
            } catch (AuthenticationException e) {
                result.put("error","Unknown auth error");
            }
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) throws IOException {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
