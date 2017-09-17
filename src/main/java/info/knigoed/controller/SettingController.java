package info.knigoed.controller;

import info.knigoed.config.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private RequestContext requestContext;


    @RequestMapping(value = "/country", method = RequestMethod.POST)
    public ModelAndView setCountry(@RequestParam String country, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
        requestContext.setCountry(country, response);
        String referer = requestContext.formatCountryUrl(country, request.getHeader("Referer"));
        return new ModelAndView("redirect:" + referer);
    }

}
