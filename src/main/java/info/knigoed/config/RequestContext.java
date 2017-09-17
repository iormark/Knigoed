package info.knigoed.config;

import info.knigoed.util.UrlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestContext extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestContext.class);

    private String country = "RU";

    private String detectCountry(HttpServletRequest request, HttpServletResponse response) {
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

        Cookie cookie1 = new Cookie("country", country);
        cookie1.setMaxAge(360 * 30);
        response.addCookie(cookie1);
        return country;
    }

    public String getCountryCode() {
        return country;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        country = detectCountry(request, response);
        LOG.info("Country {}", country);
        return true;
    }

    public void setCountry(String country, HttpServletResponse response) {
        LOG.info("Set Country {}", country);
        Cookie cookie = new Cookie("country", country);
        cookie.setMaxAge(360 * 30);
        response.addCookie(cookie);
    }

    public String formatCountryUrl(String country, String url) throws MalformedURLException {
        LOG.info("Country {}, Referer {}", country, url);
        if ("RU".equals(country))
            url = url.replaceAll("//[a-z]{2}\\.", "//www.");
        else
            url = url.replaceAll("//([a-z]{2}|www)\\.", "//" + country.toLowerCase() + ".");

        if(url.contains("/search")) {
            UrlGenerator urlGenerator = new UrlGenerator(new URL(url));
            urlGenerator.setParameter("shop", null);
            url = urlGenerator.getURL(false);
        }

        return url;
    }
}
