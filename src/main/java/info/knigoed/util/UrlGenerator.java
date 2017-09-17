package info.knigoed.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mark
 */
public class UrlGenerator {
    private final static Logger LOGGER = LoggerFactory.getLogger(UrlGenerator.class);
    private final static String DEFAULT_ENCODING_URL = "UTF-8";

    private final URL address;
    private final Map<String, String> map = new LinkedHashMap<>();

    public UrlGenerator() {
        address = null;
    }

    /**
     * Определяет параметры из URL и сохраняет в Map.
     *
     * @param address url адрес
     */
    public UrlGenerator(URL address) {
        this.address = address;

        if (null != address.getQuery()) {
            String[] query = StringUtils.split(address.getQuery(), "&");
            for (String params : query) {
                String[] param = StringUtils.split(params, "=");
                map.put(param[0], param.length > 1 ? param[1] : "");
            }
        }
    }

    public String getParameter(String param) {
        return map.get(param);
    }

    public void setParameter(String param, Object value) {
        if (value != null) {
            map.put(param, value.toString());
        } else {
            unsetParameter(param);
        }
    }

    /**
     * Добавляет новый параметр вместе со значением, пример:
     *
     * @param parVal "param=value"
     */
    public void setParameter(String parVal) {
        if (null != parVal) {
            String[] p = parVal.split("=");
            map.put(p[0], p[1]);
        }
    }

    private void unsetParameter(String param) {
        map.remove(param);
    }

    public void setParameters(Map<String, String> map) {
        this.map.putAll(map);
    }

    public Map<String, String> getParameters() {
        return map;
    }

    public String getURL(boolean isEncode) {
        String uri = "";
        if (null != address) {
            uri = address.getProtocol() + "://"
                + address.getHost()
                + address.getPath();
        }

        StringBuilder sb = new StringBuilder(uri);
        List<String> listOfParams = new ArrayList<>();
        for (String param : map.keySet()) {
            listOfParams.add(param + "=" + (isEncode ? encodeString(map.get(param)) : map.get(param)));
        }

        if (!listOfParams.isEmpty()) {
            String query = StringUtils.join(listOfParams, "&");
            sb.append("?");
            sb.append(query);
        }

        return sb.toString();
    }

    private static String encodeString(String name) throws NullPointerException {
        String tmp = null;

        if (name == null) {
            return null;
        }

        try {
            tmp = java.net.URLEncoder.encode(name, DEFAULT_ENCODING_URL);
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("", ex);
        }

        if (tmp == null) {
            throw new NullPointerException();
        }

        return tmp;
    }

    /**
     * Возвращает полный URL адрес страницы
     * *
     *
     * @param request HttpServletRequest
     * @return url
     */
    public static String getURI(HttpServletRequest request) {
        return request.getScheme() + "://"
            + request.getServerName()
            + ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort())
            + request.getRequestURI()
            + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

}
