package info.knigoed.util;

import info.knigoed.pojo.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PriceCurrency2 {

    private static final Logger LOG = LoggerFactory.getLogger(PriceCurrency2.class);

    private Map<String, Country> countries = new HashMap<>();
    private Map<String, Map<String, Float>> currencies = new HashMap<>();

    // Инфо. о стране
    private HashMap<String, String> currenciesBank = new HashMap<>();

    // Валюта клиента RUR,UAH...
    private String userCurrencyCode = null;
    private String currencySuffix = null;


    /**
     * @param countries       полный список стран
     * @param currencies      полный список валют
     * @param userCountryCode код страны пользователя
     */
    public PriceCurrency2(Map<String, Country> countries, Map<String, Map<String, Float>> currencies, String userCountryCode) {
        this.countries = countries;
        this.currencies = currencies;
        LOG.info("countries {}", countries);
        LOG.info("currencies {}", currencies);

        // Банки по которым будет конвертация валюты
        currenciesBank.put("CBRF", "RUR");
        currenciesBank.put("NBU", "UAH");
        currenciesBank.put("NBK", "KZT");
        LOG.info("currenciesBank {}", currenciesBank);
        setUserCountryCode(userCountryCode);
    }


    /**
     * @param countries  полный список стран
     * @param currencies полный список валют
     */
    public PriceCurrency2(Map<String, Country> countries, Map<String, Map<String, Float>> currencies) {
        this.countries = countries;
        this.currencies = currencies;
        LOG.info("countries {}", countries);
        LOG.info("currencies {}", currencies);

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
     * @param price          цена товара
     * @param countryCode    страна магазина цены RU,UA...
     * @param currencyCode   валюта в которой продается товар RUR,UAH...
     * @param shopCurrencies по отношению к какой валюте конвертировать
     * @return
     */
    public double getCurrency(double price, String countryCode, String currencyCode, Map<String, String> shopCurrencies) {
        //System.out.println("In Price: " + price + " " + currencyCode);

        countryCode = countryCode != null ? countryCode : "RU";
        currencyCode = !currencyCode.equals("") ? currencyCode : "RUR";
        Object bank = getRate2(countryCode, shopCurrencies);
        double curr = currencyConversion(price, currencyCode, bank);
        //System.out.println("Out Price: " + curr + " " + userCurrencyCode);
        return curr;
    }

    /**
     * Определяет по отношению к какой валюте производить конвертацию.
     * <p>
     *
     * @param priceCurrencyCode - валюта товарного предложения
     * @param shopCountryCode   страна магазина цены RU,UA...
     * @param shopCurrencies    по отношению к какой валюте конвертировать
     * @return известный курс/валюта, по которой будет конвертация
     */
    public Object getRate(String priceCurrencyCode, String shopCountryCode, Map<String, String> shopCurrencies) {
        return null;
    }


    /**
     * Определяет по отношению к какой валюте производить конвертацию.
     * <p>
     *
     * @param shopCountryCode страна магазина цены RU,UA...
     * @param shopCurrencies  по отношению к какой валюте конвертировать
     * @return известный курс/валюта, по которой будет конвертация
     */
    public Object getRate2(String shopCountryCode, Map<String, String> shopCurrencies) {
        String rate = "CB";
        String rateCode = "RUR";
        try {
            if (shopCurrencies.containsKey(userCurrencyCode)) {
                rate = shopCurrencies.get(userCurrencyCode);
                try {
                    return Double.parseDouble(rate);
                } catch (NumberFormatException e) {
                    LOG.info("", e);
                }
                if ("СВ".equals(rate) || "1".equals(rate))
                    rateCode = countries.get(shopCountryCode).getCurrencyCode();
                else {
                    if (currenciesBank.containsKey(rate))
                        rateCode = currenciesBank.get(rate);
                    else
                        rateCode = countries.get(shopCountryCode).getCurrencyCode();
                }
            } else {
                rateCode = countries.get(shopCountryCode).getCurrencyCode();
            }
        } catch (ClassCastException e) {
            LOG.info("", e);
        }
        return rateCode;
    }


    /**
     * Конвертирует валюты
     * <p>
     *
     * @param price
     * @param currencyCode
     * @param rate
     * @return
     */
    private double currencyConversion(double price, String currencyCode, Object rate) {
        if (userCurrencyCode.equals(rate) && rate.equals(currencyCode)) {
            return price;
        } else if (!userCurrencyCode.equals(rate) && !currencyCode.equals(rate)) {
            // если запрошенная валюта и валюта товара не равны валюте банка,
            // нужно конвертировать в начале в валюту банка, а потом в необходимую валюту
            price = price * Double.parseDouble(currencies.get(rate).get(currencyCode).toString());
            //System.out.println("из RUR в UAH: " + price);
            price = price / Double.parseDouble(currencies.get(rate).get(userCurrencyCode).toString());
            //System.out.println("из UAH в BYR: " + price);
        } else if (userCurrencyCode.equals(rate)) {
            // если валюта в которую переводим равна банку
            // по которому переводим, такой запрос будет NULL
            price = price * Double.parseDouble(currencies.get(rate).get(currencyCode).toString());
        } else {
            price = price / Double.parseDouble(currencies.get(rate).get(userCurrencyCode).toString());
        }

        return price;
    }

    public String getCurrencySuffix() {
        return currencySuffix;
    }
}