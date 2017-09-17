package info.knigoed.controller;

import info.knigoed.pojo.User;
import info.knigoed.service.CountryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TemplateController {
    private static final Logger LOG = LoggerFactory.getLogger(TemplateController.class);
    @Autowired
    protected CountryService countryService;
    @Autowired
    protected WebSecurityManager securityManager;

    @ModelAttribute
    public void addAttributes(Model model) {
        //SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated())
            model.addAttribute("user", (User) currentUser.getPrincipal());

        model.addAttribute("countryCode", countryService.getCountryCode());
        model.addAttribute("countries", countryService.getCountries());
    }
}
