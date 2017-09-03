package info.knigoed.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchSphinxParam {
    private static final Logger LOG = LoggerFactory.getLogger(SearchSphinxParam.class);
    private String key;
    private int shopId = 0;

		/*search.filterAvailable(available);
        search.setQuorum(quorum);
		search.setRating(rating);*/

    /**
     * @param key      - query
     * @param keywords - old query params
     * @param shop     - shop
     * @param year     - year
     */
    public SearchSphinxParam(String key, String keywords, Integer shop, Integer year) {
        if (StringUtils.isEmpty(key) && !StringUtils.isEmpty(keywords))
            key = keywords;

        LOG.info("{}, shopId:{}", key, shop);
        this.key = queryClear(key);
        if (shop != null)
            shopId = shop;
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

    public int getShopId() {
        return shopId;
    }
}
