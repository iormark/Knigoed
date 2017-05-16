package info.knigoed.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

@Service
public class RequestContext extends HandlerInterceptorAdapter {

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

	public String getCountry() {
		return country;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		country = detectCountry(request, response);

		System.out.println("request.getContextPath()");

		return true;
	}

}
