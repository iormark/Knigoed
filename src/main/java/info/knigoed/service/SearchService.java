package info.knigoed.service;

import info.knigoed.config.CountryConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.dao.SearchDao;
import info.knigoed.dao.SearchSphinxDao;
import info.knigoed.exception.ResourceNotFoundException;
import info.knigoed.pojo.Book;
import info.knigoed.util.SearchSphinxParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if (!searchSphinxDao.runSearch(param.getKey(), 0, 10))
            throw new ResourceNotFoundException();

    }

    public List<Book> getBooks() throws SQLException, IOException {
        return rewriteBooks(
            searchDao.getBooks(searchSphinxDao.getBooksId()),
            searchSphinxDao.getYears());
    }

    private List<Book> rewriteBooks(List<Book> books, Map<Integer, String> years) throws IOException {
        List<Book> list = new ArrayList<>();
        for (Book book : books) {
            book.setYears(years.get(book.getBookId()));
            list.add(book);
        }
        LOG.debug("rewriteBooks: {}", list);
        return list;
    }

}
