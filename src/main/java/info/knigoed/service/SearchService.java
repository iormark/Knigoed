package info.knigoed.service;

import info.knigoed.config.CountryConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.dao.SearchDao;
import info.knigoed.dao.SearchSphinxDao;
import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import info.knigoed.util.SearchSphinxParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
public class SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);

    private final RequestContext requestContext;
    private final CountryConfig countryConfig;
    private final SearchDao searchDao;
    private final SearchSphinxDao searchSphinxDao;


    @Autowired
    public SearchService(RequestContext requestContext, CountryConfig countryConfig, SearchDao searchDao, SearchSphinxDao searchSphinxDao) {
        this.requestContext = requestContext;
        this.countryConfig = countryConfig;
        this.searchDao = searchDao;
        this.searchSphinxDao = searchSphinxDao;
    }

    public void runSearch(SearchSphinxParam param) throws SQLException {
        if (countryConfig.getCountries().containsKey(requestContext.getCountry()))
            this.searchSphinxDao.filterCountry(countryConfig.getCountries().get(requestContext.getCountry()).getCountryId());
        else
            LOG.error("Country Not Found");


        this.searchSphinxDao.filterShop(param.getShopId());
        searchSphinxDao.runSearch(param.getKey(), 0, 10);
    }

    public List<Book> getBooks() throws SQLException, IOException {
        HashMap<Integer, TreeSet<Price>> prices = groupPrices(searchDao.getPrices(searchSphinxDao.getBooksId()));

        return rewriteBooks(
            searchDao.getBooks(searchSphinxDao.getBooksId()), prices,
            searchSphinxDao.getYears());
    }

    private HashMap<Integer, TreeSet<Price>> groupPrices(List<Price> prices) throws IOException {
        HashMap<Integer, TreeSet<Price>> map = new HashMap<>();
        for (Price price : prices) {
            TreeSet<Price> treeSet;
            if (!map.containsKey(price.getBookId())) {
                treeSet = new TreeSet<Price>(new PricesSortAsc());
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

    private List<Book> rewriteBooks(List<Book> books, HashMap<Integer, TreeSet<Price>> prices, Map<Integer, String> years) throws IOException {
        List<Book> list = new ArrayList<>();
        for (Book book : books) {
            book.setPrices(prices.get(book.getBookId()));
            book.setYears(years.get(book.getBookId()));
            list.add(book);
        }
        LOG.debug("rewriteBooks: {}", list);
        return list;
    }

    public class PricesSortAsc implements Comparator<Price> {
        @Override
        public int compare(Price o1, Price o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }
}
