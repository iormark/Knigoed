package info.knigoed.service;

import info.knigoed.dao.CountryDao;
import info.knigoed.config.RequestContext;
import info.knigoed.dao.SearchDao;
import info.knigoed.dao.SearchSphinxDao;
import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import info.knigoed.pojo.Shop;
import info.knigoed.util.SearchSphinxParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
public class SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);

    private final RequestContext requestContext;
    private final CountryDao countryConfig;
    private final SearchDao searchDao;
    private final SearchSphinxDao searchSphinxDao;

    private String message = "";

    @Autowired
    public SearchService(RequestContext requestContext, CountryDao countryConfig, SearchDao searchDao, SearchSphinxDao searchSphinxDao) {
        this.requestContext = requestContext;
        this.countryConfig = countryConfig;
        this.searchDao = searchDao;
        this.searchSphinxDao = searchSphinxDao;
    }

    public boolean runSearch(SearchSphinxParam param) {
        try {
            if (!param.isValid())
                throw new SQLException("Search query is not valid");

            if (countryConfig.getCountries().containsKey(requestContext.getCountry()))
                searchSphinxDao.filterCountry(countryConfig.getCountries().get(requestContext.getCountry()).getCountryId());
            else
                LOG.error("Country Not Found");

            searchSphinxDao.filterShop(param.getShopId());

            searchSphinxDao.runSearch(param.getKey(), 0, 10);

        } catch (SQLException | EmptyResultDataAccessException e) {
            LOG.debug("Query: {}", param.getKey(), e);
            return false;
        }
        return true;
    }

    /*======== Book ========*/

    public List<Book> getBooks() throws SQLException, IOException {
        if (StringUtils.isEmpty(searchSphinxDao.getBooksId()))
            return new ArrayList<>();

        HashMap<Integer, TreeSet<Price>> prices = groupPrices(searchDao.getPrices(searchSphinxDao.getBooksId()));

        return rewriteBooks(
            searchDao.getBooks(searchSphinxDao.getBooksId()), prices,
            searchSphinxDao.getYears());
    }

    private List<Book> rewriteBooks(List<Book> books, HashMap<Integer, TreeSet<Price>> prices, Map<Integer, String> years) throws IOException {
        List<Book> list = new ArrayList<>();
        for (Book book : books) {
            book.setPrices(prices.get(book.getBookId()));
            list.add(book);
        }
        LOG.debug("rewriteBooks: {}", list);
        return list;
    }

    /*======== Price ========*/

    private HashMap<Integer, TreeSet<Price>> groupPrices(List<Price> prices) throws IOException {
        HashMap<Integer, TreeSet<Price>> map = new HashMap<>();
        for (Price price : prices) {
            price.setAvailable("true".equals(price.getAvailable()) ? "В наличии" : "На заказ");

            TreeSet<Price> treeSet;
            if (!map.containsKey(price.getBookId())) {
                treeSet = new TreeSet<>(new PricesSortAsc());
                treeSet.add(price);
                map.put(price.getBookId(), treeSet);
            } else {
                treeSet = map.get(price.getBookId());
                treeSet.add(price);
                map.put(price.getBookId(), treeSet);
            }
        }
        LOG.debug("groupPrices: {}", map);
        return map;
    }

    public class PricesSortAsc implements Comparator<Price> {
        @Override
        public int compare(Price o1, Price o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }

    /*======== Shop ========*/

    public List<Shop> getShops() throws SQLException, IOException {
        if (searchSphinxDao.getShopsId().isEmpty())
            return new ArrayList<>();

        List<Shop> shops = searchDao.getShops(searchSphinxDao.getShopsId(), requestContext.getCountry());
        LOG.debug("shops: {}", shops);
        return shops;
    }

    /*======== Get ========*/
    public long getTotal() {
        return searchSphinxDao.getTotal();
    }
}
