package info.knigoed.util;

import info.knigoed.pojo.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Конвертер цен
 * @author Iordan Mark
 */

public class PriceCurrency {

    private static final Logger LOG = LoggerFactory.getLogger(PriceCurrency.class);

    private Map<String, Country> countries = new HashMap<>();
    private Map<String, Map<String, Float>> currencies = new HashMap<>();

    // Инфо. о стране
    private HashMap<String, String> currenciesBank = new HashMap<>();

    // Валюта клиента RUR,UAH...
    private String userCurrencyCode = null;
    private String currencySuffix = null;

    /**
     * @param countries  полный список стран
     * @param currencies полный список валют
     */
    public PriceCurrency(Map<String, Country> countries, Map<String, Map<String, Float>> currencies) {
        this.countries = countries;
        this.currencies = currencies;
        LOG.info("countries {}", countries);
        LOG.info("currencies {}", currencies);

        // TODO: можно переместить в базу
        // Банки по которым будет конвертация валюты
        currenciesBank.put("CBRF", "RUR");
        currenciesBank.put("NBU", "UAH");
        currenciesBank.put("NBK", "KZT");
        LOG.info("currenciesBank {}", currenciesBank);
    }


    public void setUserCountryCode(String userCountryCode) {
        try {
            userCurrencyCode = countries.get(userCountryCode).getCurrencyCode();
            currencySuffix = countries.get(userCountryCode).getCurrencySuffix();
        } catch (Exception ex) {
            userCurrencyCode = "RUR";
            currencySuffix = "руб.";
        }
        LOG.info("userCurrencyCode {}, currencySuffix {}", userCurrencyCode, currencySuffix);
    }

    /**
     * Конвертация валюты
     * <p>
     *
     * @param price             цена товара
     * @param priceCurrencyCode валюта в которой продается товар RUR,UAH...
     * @param shopCountryCode   страна магазина цены RU,UA...
     * @param shopCurrencies    по отношению к какой валюте конвертировать
     * @return сконвертированная цена
     */
    public double getCurrency(double price, String priceCurrencyCode, String shopCountryCode, Map<String, String> shopCurrencies) {
        shopCountryCode = shopCountryCode != null ? shopCountryCode : "RU";
        priceCurrencyCode = !priceCurrencyCode.equals("") ? priceCurrencyCode : "RUR";
        String rateCode = getRate(priceCurrencyCode, shopCountryCode, shopCurrencies);
        LOG.info("rateCode {}", rateCode);
        double curr = currencyConversion(price, priceCurrencyCode, rateCode);
        return curr;
    }

    /**
     * Определяет по отношению к какой валюте производить конвертацию.
     * <p>
     *
     * @param priceCurrencyCode - валюта товарного предложения
     * @param shopCountryCode   страна магазина цены RU,UA...
     * @param shopCurrencies    по отношению к какой валюте конвертировать
     * @return валюта, по которой будет конвертация
     */
    public String getRate(String priceCurrencyCode, String shopCountryCode, Map<String, String> shopCurrencies) {
        String rate;
        String rateCode = "RUR";
        try {
            rateCode = countries.get(shopCountryCode).getCurrencyCode();
            if (shopCurrencies.containsKey(priceCurrencyCode)) {
                rate = shopCurrencies.get(priceCurrencyCode);
                if ("СВ".equals(rate) || "1".equals(rate))
                    return rateCode;
                else
                    return currenciesBank.getOrDefault(rate, rateCode);
            }
        } catch (ClassCastException e) {
            LOG.error("", e);
        }
        return rateCode;
    }


    /**
     * Конвертирует валюты
     * <p>
     *
     * @param price цена за товарное предложение
     * @param priceCurrencyCode валюта в которой продается товар RUR,UAH...
     * @param rateCode валюта, по которой будет конвертация
     * @return сконвертированная цена за товарное предложение
     */
    private double currencyConversion(double price, String priceCurrencyCode, String rateCode) {
        if (userCurrencyCode.equals(rateCode) && rateCode.equals(priceCurrencyCode)) {
            return price;
        } else if (!userCurrencyCode.equals(rateCode) && !priceCurrencyCode.equals(rateCode)) {
            // если запрошенная валюта и валюта товара не равны валюте банка,
            // нужно конвертировать в начале в валюту банка, а потом в необходимую валюту
            price = price * Double.parseDouble(currencies.get(rateCode).get(priceCurrencyCode).toString());
            //System.out.println("из RUR в UAH: " + price);
            price = price / Double.parseDouble(currencies.get(rateCode).get(userCurrencyCode).toString());
            //System.out.println("из UAH в BYR: " + price);
        } else if (userCurrencyCode.equals(rateCode)) {
            // если валюта в которую переводим равна банку
            // по которому переводим, такой запрос будет NULL
            price = price * Double.parseDouble(currencies.get(rateCode).get(priceCurrencyCode).toString());
        } else {
            // TODO
            LOG.info("currencies {}", currencies.get(rateCode));
            LOG.info("userCurrencyCode {}", userCurrencyCode);
            price = price / Double.parseDouble(currencies.get(rateCode).get(userCurrencyCode).toString());
        }

        return price;
    }

    public String getCurrencySuffix() {
        return currencySuffix;
    }
}