package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.BookDao;
import info.knigoed.dao.CountryDao;
import info.knigoed.dao.CurrencyDao;
import info.knigoed.pojo.Price;
import info.knigoed.pojo.Shop;
import info.knigoed.util.Jackson;
import info.knigoed.util.PriceCurrency;
import info.knigoed.util.PriceUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PriceService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);
    private final RequestContext requestContext;
    private final BookDao bookDao;
    private final CountryDao countryDao;
    private final CurrencyDao currencyDao;

    @Autowired
    public PriceService(RequestContext requestContext, BookDao bookDao, CountryDao countryDao, CurrencyDao currencyDao) {
        this.requestContext = requestContext;
        this.bookDao = bookDao;
        this.countryDao = countryDao;
        this.currencyDao = currencyDao;
    }


    public List<Price> getPrices(int bookId) throws IOException {
        LOG.debug("getPrices: bookId {}, country {}", bookId, requestContext.getCountry());
        return rewritePrices(bookDao.readPrices(bookId, requestContext.getCountry(), "ASC", 100));
    }

    private List<Price> rewritePrices(List<Price> prices) throws IOException {
        PriceCurrency priceCurrency = new PriceCurrency(countryDao.getCountries(), currencyDao.getCurrencies());
        priceCurrency.setUserCountryCode("UA");

        List<Price> list = new ArrayList<>();
        for (Price price : prices) {

            price.setAvailable("true".equals(price.getAvailable()) ? "В наличии" : "На заказ");

            price.setName(StringUtils.isEmpty(price.getName()) ? price.getDomain() : price.getName());

            Map shopCurrencies = Jackson.mapper().readValue(price.getSetting(), Shop.Settings.class).getCurrencies();

            double curr = priceCurrency.getCurrency(
                price.getPrice(),
                price.getCurrencyCode(),
                price.getCountryCode(),
                shopCurrencies
            );

            price.setPrice(curr);
            price.setPriceFormat(PriceUtils.priceFormat(curr, requestContext.getCountry()) + " "
                + priceCurrency.getCurrencySuffix());
            list.add(price);
        }
        LOG.debug("rewritePrices: {}", list);
        return list;
    }

}
