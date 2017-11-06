package info.knigoed.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchParam {
    private static final Logger LOG = LoggerFactory.getLogger(SearchParam.class);
    private String key;

    private Type type = Type.all;

    public enum Type {
        all, book, ebook, audiobook
    }

    private Integer year;
    private Integer shopId = 0;

		/*search.filterAvailable(available);
        search.setQuorum(quorum);
		search.setRating(rating);*/

    /**
     * @param key      - query
     * @param keywords - old query params
     * @param shopId   - shop
     * @param year     - year
     */
    public SearchParam(String key, String keywords, Type type, Integer shopId, Integer year) {
        if (StringUtils.isEmpty(key) && !StringUtils.isEmpty(keywords))
            key = keywords;

        LOG.info("{}, shopId:{}", key, shopId);
        this.key = queryClear(key);
        this.type = type;
        this.shopId = shopId;
        this.year = year;
    }

    private String queryClear(String key) {
        if (null != key) {
            key = key.trim() + " ";
            key = key.replace("&quot;", " ").
                replaceAll("[\")(~!'/]+", " ").
                replaceAll("[@]+", " ").
                replace("--", " ").trim();
        }
        return key;
    }

    public boolean isValid() {
        return !StringUtils.isEmpty(key);
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        if (type == null)
            return "all";
        else
            return type.name();
    }

    public int getShopId() {
        return shopId != null ? shopId : 0;
    }

    public Integer getYear() {
        // TODO: In 3017 there will be a bug
        return year != null && year > 0 && year < 3017 ? year : null;
    }
}
