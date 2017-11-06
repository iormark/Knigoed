package info.knigoed.util;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class NextPage implements Pagination {
    private static final Logger LOG = LoggerFactory.getLogger(NextPage.class);
    private String url;
    private int page = 0;
    private int limit = 0;
    private long countItems = 0;

    public NextPage(String url, int page, int limit) throws URISyntaxException, NullPointerException {
        this.url = url;
        this.page = page;
        this.limit = limit;
    }

    public Result getNextPage(long countItems) throws URISyntaxException {
        Result result = new Result();
        URIBuilder uriBuilder = new URIBuilder(url);
        LOG.debug("url {}, page {}, limit {}", url, page, limit);
        result.setPage(page);

        int pages = getCountPage(countItems);
        LOG.debug("Pages {}", pages);
        if (page > 1)
            result.setBackURL(uriBuilder.setParameter("page", Integer.toString(page - 1)).toString());

        if (page < pages)
            result.setNextURL(uriBuilder.setParameter("page", Integer.toString(page + 1)).toString());

        LOG.debug(result.toString());
        return result;
    }

    private int getCountPage(long countItems) {
        return (int) ((countItems + (limit - 1)) / limit);
    }

    public int getOffset() {
        int offset = page > 0 ? page * limit : 0;
        offset = page == 1 ? 0 : offset - limit;
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public class Result {
        private int page = 1;
        private String backURL = null;
        private String nextURL = null;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getBackURL() {
            return backURL;
        }

        public void setBackURL(String backURL) {
            this.backURL = backURL;
        }

        public String getNextURL() {
            return nextURL;
        }

        public void setNextURL(String nextURL) {
            this.nextURL = nextURL;
        }

        @Override
        public String toString() {
            return "{page=" + page + ", backURL=" + backURL + ", nextURL=" + nextURL + "}";
        }
    }
}
