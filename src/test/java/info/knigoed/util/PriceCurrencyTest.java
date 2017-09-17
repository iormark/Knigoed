package info.knigoed.util;

import info.knigoed.config.DataSourceConfig;
import info.knigoed.config.OtherConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.config.WebConfig;
import info.knigoed.dao.CountryDao;
import info.knigoed.dao.CurrencyDao;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, OtherConfig.class, DataSourceConfig.class, RequestContext.class})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriceCurrencyTest {

    @Autowired
    private CountryDao countryDao;
    @Autowired
    private CurrencyDao currencyDao;
    private PriceCurrency priceCurrency;


    @Before
    public void initPriceCurrency() {
        priceCurrency = new PriceCurrency(countryDao.getCountries(), currencyDao.getCurrencies());
    }

    @Test
    public void testGetRateNull() {
        priceCurrency.setUserCountryCode("UA");
        Map<String, String> shopCurrencies = new HashMap<>();
        shopCurrencies.put("RUR", "1");
        shopCurrencies.put("EUR", "CBRF");
        assertTrue("RUR".equals(priceCurrency.getRate("RUR","RU", shopCurrencies)));
    }

    @Test
    public void testGetRateRUR() {
        priceCurrency.setUserCountryCode("UA");
        Map<String, String> shopCurrencies = new HashMap<>();
        shopCurrencies.put("RUR", "1");
        shopCurrencies.put("EUR", "CBRF");
        assertTrue("RUR".equals(priceCurrency.getRate("RUR","RU", shopCurrencies)));
    }

    @Test
    public void testCGetRateYAH() {
        priceCurrency.setUserCountryCode("UA");
        Map<String, String> shopCurrencies = new HashMap<>();
        shopCurrencies.put("RUR", "1");
        shopCurrencies.put("UAH", "NBU");
        assertTrue("UAH".equals(priceCurrency.getRate("UAH","RU", shopCurrencies)));
    }


}