package info.knigoed.config;

import info.knigoed.dao.CountryDao;
import info.knigoed.dao.UserDao;
import info.knigoed.pojo.Country;
import info.knigoed.pojo.User;
import info.knigoed.util.UrlGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestContext extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestContext.class);


    @Autowired
    private CountryDao countryDao;
    @Autowired
    private UserDao userDao;
    //@Autowired
    //protected WebSecurityManager securityManager;

    private String country = "RU";
    // User subject
    private Subject subject;
    private User user;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        detectCountry(request, response);
        setUser();
        return true;
    }

    // ==== Country ====

    private void detectCountry(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, "country");

        String country = "RU";
        Matcher matcher = Pattern.compile("^([a-z]{2})\\.").matcher(request.getHeader("Host"));
        if (matcher.find()) {
            country = matcher.group(1).toUpperCase();
        } else if (/*!"last".equals(args.get("model"))
                && !"book".equals(args.get("model"))
				&& !"home".equals(args.get("model"))
				&& */null != cookie) {
            country = cookie.getValue();
        }

        setCountry(country, response);
        this.country = country;
        LOG.info("Country {}", country);
    }

    public String getCountryCode() {
        return country;
    }

    public List<Country> getCountries() {
        return rewriteCountries(countryDao.getCountries());
    }

    private List<Country> rewriteCountries(Map<String, Country> countries) {
        List<Country> list = new ArrayList<>();
        for (Map.Entry<String, Country> entry : countries.entrySet()) {
            Country country = entry.getValue();
            country.setSelected(entry.getKey().equals(getCountryCode()));
            list.add(country);
        }
        return list;
    }

    public void setCountry(String country, HttpServletResponse response) {
        LOG.info("Set Country {}", country);
        Cookie cookie = new Cookie("country", country);
        cookie.setMaxAge(3600 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String formatCountryUrl(String country, String url) throws MalformedURLException {
        LOG.info("Country {}, Referer {}", country, url);
        if ("RU".equals(country))
            url = url.replaceAll("//[a-z]{2}\\.", "//www.");
        else
            url = url.replaceAll("//([a-z]{2}|www)\\.", "//" + country.toLowerCase() + ".");

        if (url.contains("/search")) {
            UrlGenerator urlGenerator = new UrlGenerator(new URL(url));
            urlGenerator.setParameter("shop", null);
            url = urlGenerator.getURL(false);
        }

        return url;
    }


    // ==== User ====

    private void setUser() {
        //SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
        LOG.debug("isRemembered {}", subject.isRemembered());
        if (subject.isAuthenticated()) {
            user = userDao.getByUserId(((User) subject.getPrincipal()).getUserId());
        }

    }

    public Subject getSubject() {
        return subject;
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return user.getUserId();
    }

    public boolean hasRole(String role) {
        return subject.hasRole(role);
    }

    public boolean hasRoles(Collection<String> roles) {
        return subject.hasAllRoles(roles);
    }


}
