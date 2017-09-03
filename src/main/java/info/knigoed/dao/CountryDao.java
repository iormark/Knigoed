package info.knigoed.dao;

import info.knigoed.pojo.Country;
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
public class CountryDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    private Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    private void initCountries() {
        String sql = "SELECT * FROM Country LIMIT 10";
        rewriteCountries(mysqlJdbc.query(sql, new BeanPropertyRowMapper(Country.class)));
    }

    private void rewriteCountries(List<Country> countriesList) {
        for (Country country : countriesList) {
            countries.put(country.getCountryCode(), country);
        }
    }

    public Map<String, Country> getCountries() {
        return countries;
    }
}
