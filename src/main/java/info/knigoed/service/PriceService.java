package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.BookDao;
import info.knigoed.dao.CountryDao;
import info.knigoed.dao.CurrencyDao;
import info.knigoed.dao.PriceDao;
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
import java.util.*;

@Service
public class PriceService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);
    private final RequestContext requestContext;
    private final BookDao bookDao;
    private final PriceDao priceDao;
    private final CountryDao countryDao;
    private final CurrencyDao currencyDao;
    private final PriceCurrency priceCurrency;

    @Autowired
    public PriceService(RequestContext requestContext, BookDao bookDao, PriceDao priceDao, CountryDao countryDao, CurrencyDao currencyDao) {
        this.requestContext = requestContext;
        this.bookDao = bookDao;
        this.priceDao = priceDao;
        this.countryDao = countryDao;
        this.currencyDao = currencyDao;
        priceCurrency = new PriceCurrency(countryDao.getCountries(), currencyDao.getCurrencies());
    }

    /**
     * Rewrite prices for one book
     *
     * @return list price one book
     * @throws IOException
     */
    public List<Price> getPrices(int bookId) throws IOException {
        LOG.debug("getPrices: bookId {}, country {}", bookId, requestContext.getCountryCode());
        priceCurrency.setUserCountryCode(requestContext.getCountryCode());
        List<Price> list = new ArrayList<>();
        for (Price price : priceDao.getPrices(bookId, requestContext.getCountryCode(), "ASC", 100)) {
            try {
                list.add(rewritePrice(price));
            } catch (Exception e) {
                LOG.error("", e);
            }
        }
        return list;
    }

    /**
     * Price grouping for each book
     *
     * @param prices list price
     * @return prices grouped by bookId
     * @throws IOException
     */
    public HashMap<Integer, TreeSet<Price>> groupPrices(List<Price> prices) throws IOException {
        priceCurrency.setUserCountryCode(requestContext.getCountryCode());
        HashMap<Integer, TreeSet<Price>> map = new HashMap<>();
        for (Price price : prices) {

            price = rewritePrice(price);

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

            LOG.debug("treeSet {}", treeSet);
        }
        return map;
    }

    private Price rewritePrice(Price price) throws IOException {
        price.setAvailable("true".equals(price.getAvailable()) ? "В наличии" : "На заказ");

        price.setName(StringUtils.isEmpty(price.getName()) ? price.getDomain() : price.getName());

        Map shopCurrencies = Jackson.mapper().readValue(price.getSetting(), Shop.Setting.class).getCurrencies();

        double curr = priceCurrency.getCurrency(
            price.getPrice(),
            price.getCurrencyCode(),
            price.getCountryCode(),
            shopCurrencies
        );

        price.setPrice(curr);
        price.setPriceFormat(PriceUtils.priceFormat(curr, requestContext.getCountryCode()) + " "
            + priceCurrency.getCurrencySuffix());
        return price;
    }

    public class PricesSortAsc implements Comparator<Price> {
        @Override
        public int compare(Price o1, Price o2) {
            if (o2.getPrice() < o1.getPrice()) {
                return 1;
            } else if (o2.getPrice() > o1.getPrice()) {
                return -1;
            } else if(o1.getUrl().equals(o2.getUrl())) {
                return 0;
            }
            return 1;
        }
    }

}
