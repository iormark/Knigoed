package info.knigoed.dao;

import info.knigoed.pojo.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    private Map<String, Map<String, Float>> currencies = new HashMap<>();

    @PostConstruct
    private void initCurrency() {
        String sql = "SELECT * FROM Currency LIMIT 10";
        rewriteCurrencies(mysqlJdbc.query(sql, new BeanPropertyRowMapper(Currency.class)));
    }

    private void rewriteCurrencies(List<Currency> currencyList) {
        for (Currency currency : currencyList) {
            String currencyFrom = currency.getCurrencyFrom();
            if (currencies.containsKey(currencyFrom))
                currencies.get(currencyFrom).put(currency.getCurrencyTo(), currency.getCurrencyValue());
            else {
                Map<String, Float> tmp = new HashMap<>();
                tmp.put(currency.getCurrencyTo(), currency.getCurrencyValue());
                currencies.put(currencyFrom, tmp);
            }
        }
    }

    public Map<String, Map<String, Float>> getCurrencies() {
        return currencies;
    }
}
