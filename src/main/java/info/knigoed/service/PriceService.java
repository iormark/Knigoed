package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.BookDao;
import info.knigoed.pojo.Price;
import info.knigoed.pojo.Shop;
import info.knigoed.util.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final RequestContext requestContext;
    private final BookDao bookDao;

    @Autowired
    public PriceService(RequestContext requestContext, BookDao bookDao) {
        this.requestContext = requestContext;
        this.bookDao = bookDao;
    }

    public List<Price> getPrices(int bookId) throws IOException {
        LOG.debug("getPrices: bookId {}, country {}", bookId, requestContext.getCountry());
        return rewritePrices(bookDao.readPrices(bookId, requestContext.getCountry(), "ASC", 100));
    }

    private List<Price> rewritePrices(List<Price> prices) throws IOException {
        List<Price> list = new ArrayList();
        for (Price price : prices) {

            price.setName(StringUtils.isEmpty(price.getName()) ? price.getDomain() : price.getName());

            price.setCurrencies(Jackson.mapper().readValue(price.getSetting(), Shop.Settings.class).getCurrencies());

            list.add(price);
        }
        LOG.debug("rewritePrices: {}", list);
        return list;
    }

}
