package info.knigoed.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestUtils {

    public static String getURI(HttpServletRequest request) {
        return request.getScheme() + "://"
            + request.getServerName()
            + ("http".equals(request.getScheme())
            && request.getServerPort() == 80
            || "https".equals(request.getScheme())
            && request.getServerPort() == 443 ? "" : ":" + request.getServerPort())
            + request.getRequestURI()
            + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

    public static String getHost(HttpServletRequest request) {
        return request.getScheme() + "://"
            + request.getServerName()
            + ("http".equals(request.getScheme())
            && request.getServerPort() == 80
            || "https".equals(request.getScheme())
            && request.getServerPort() == 443 ? "" : ":" + request.getServerPort());
    }

    /**
     * Преобразует домен РФ к машинопонятному виду
     * <p>
     *
     * @param domain
     * @param action
     * @return
     * @throws MalformedURLException
     */
    public static String domenToASCII(String domain, boolean action) throws MalformedURLException, NullPointerException {
        if (!domain.startsWith("http://") && !domain.startsWith("https://"))
            domain = "http://" + domain;

        URL url = new URL(domain);

        if (StringUtils.isEmpty(url.getHost()))
            throw new MalformedURLException();

        return url.getProtocol() + "://"
            + (action ? IDN.toASCII(url.getHost()) : IDN.toUnicode(url.getHost()))
            + (StringUtils.isEmpty(url.getPath()) ? "" : url.getPath())
            + (StringUtils.isEmpty(url.getQuery()) ? "" : "?" + url.getQuery());
    }

    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new LinkedHashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        map.put("Remote-Addr", request.getRemoteAddr());

        return map;
    }
}
