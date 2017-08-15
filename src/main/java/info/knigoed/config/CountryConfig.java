package info.knigoed.config;

import info.knigoed.pojo.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryConfig {

    @Autowired
    private Sql2o sql2o;

    private Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    private void initCountries() {
        String sql = "SELECT countryId, countryName, countryCode FROM Country LIMIT 10";
        try (Connection con = sql2o.open()) {
            rewriteCountries(con.createQuery(sql).executeAndFetch(Country.class));
        }
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
