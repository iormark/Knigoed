package info.knigoed.controller;

import info.knigoed.config.RequestContext;
import info.knigoed.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TemplateController {
    @Autowired
    protected RequestContext requestContext;

    @ModelAttribute
    public void addAttributes(Model model) {
        User user = requestContext.getUser();
        System.out.println(">>>"+user);
        if (user != null)
            model.addAttribute("user", user);
        model.addAttribute("countryCode", requestContext.getCountryCode());
        model.addAttribute("countries", requestContext.getCountries());
    }
}
