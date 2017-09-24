package info.knigoed.controller;

import info.knigoed.dao.Invoice;
import info.knigoed.dto.Message;
import info.knigoed.pojo.SignInForm;
import info.knigoed.pojo.User;
import info.knigoed.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AuthController extends TemplateController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private Invoice invoice;


    // Sign In
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model) throws IOException {
        model.addAttribute("title", "12");

        model.addAttribute("bundle", "signin");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Message signin(@Valid SignInForm signInForm, BindingResult bindingResult, Message messages) throws IOException {
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            messages.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        if(!bindingResult.hasErrors()) {
            messages.setError(userService.authUser(signInForm));
        }
        return messages;
    }



    // Sign Up
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) throws IOException {
        model.addAttribute("title", "");

        model.addAttribute("bundle", "signup");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Message signup(@Valid User user, BindingResult bindingResult, Message messages) throws IOException {
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            messages.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        if(!bindingResult.hasErrors()) {
            messages.setError(userService.setUser(user));
        }
        return messages;
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) throws IOException {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
