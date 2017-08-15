package info.knigoed.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchSphinxParam {
    private static final Logger LOG = LoggerFactory.getLogger(SearchSphinxParam.class);
    private String key;
    private int shopId = 0;

		/*search.filterAvailable(available);
        search.setQuorum(quorum);
		search.setRating(rating);*/

    public SearchSphinxParam(String key, Integer countryId, Integer shopId, Integer year) {
        LOG.info("{}, shopId:{}", key, shopId);
        this.key = queryClear(key);
        if (shopId != null)
            this.shopId = shopId;
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

    public String getKey() {
        return key;
    }

    public int getShopId() {
        return shopId;
    }
}
