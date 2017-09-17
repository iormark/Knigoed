package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.*;
import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import info.knigoed.pojo.Shop;
import info.knigoed.util.SearchParam;
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
    private final CountryDao countryDao;
    private final SearchDao searchDao;
    private final BookDao bookDao;
    private final PriceDao priceDao;
    private final SearchSphinxDao searchSphinxDao;
    private final PriceService priceService;

    @Autowired
    public SearchService(RequestContext requestContext, CountryDao countryDao, SearchDao searchDao, BookDao bookDao, PriceDao priceDao, SearchSphinxDao searchSphinxDao, PriceService priceService) {
        this.requestContext = requestContext;
        this.countryDao = countryDao;
        this.searchDao = searchDao;
        this.bookDao = bookDao;
        this.priceDao = priceDao;
        this.searchSphinxDao = searchSphinxDao;
        this.priceService = priceService;
    }

    public boolean runSearch(SearchParam param) {
        try {
            if (!param.isValid())
                throw new SQLException("Search query is not valid");

            if (countryDao.getCountries().containsKey(requestContext.getCountryCode()))
                searchSphinxDao.filterCountry(countryDao.getCountries().get(requestContext.getCountryCode()).getCountryId());
            else
                LOG.error("Country Not Found");

            searchSphinxDao.filterType(param.getType());
            searchSphinxDao.filterShop(param.getShopId());
            searchSphinxDao.filterYear(param.getYear());

            searchSphinxDao.runSearch(param.getKey(), 0, 10);

        } catch (SQLException | EmptyResultDataAccessException e) {
            LOG.debug("Query: {}", param.getKey(), e);
            return false;
        }
        return true;
    }

    /*======== Book ========*/

    public List<Book> getBooks() throws SQLException, IOException {
        if (searchSphinxDao.getBooksId().isEmpty())
            return new ArrayList<>();

        HashMap<Integer, TreeSet<Price>> prices = priceService.groupPrices(priceDao.getPrices(searchSphinxDao.getBooksId(),
            requestContext.getCountryCode()));

        return rewriteBooks(
            bookDao.getBooks(searchSphinxDao.getBooksId()), prices,
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

    /*======== Shop ========*/

    public List<Shop> getShops() throws SQLException, IOException {
        if (searchSphinxDao.getShopsId().isEmpty())
            return new ArrayList<>();

        List<Shop> shops = searchDao.getShops(searchSphinxDao.getShopsId(), requestContext.getCountryCode());
        LOG.debug("shops: {}", shops);
        return shops;
    }

    /*======== Get ========*/
    public long getTotal() {
        return searchSphinxDao.getTotal();
    }
}
