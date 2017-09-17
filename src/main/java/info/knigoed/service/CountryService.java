package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.CountryDao;
import info.knigoed.pojo.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {

    private static final Logger LOG = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryDao countryDao;
    @Autowired
    private RequestContext requestContext;


    public List<Country> getCountries() {
        return rewriteCountries(countryDao.getCountries());
    }

    private List<Country> rewriteCountries(Map<String, Country> countries) {
        List<Country> list = new ArrayList<>();

        for (Map.Entry<String, Country> entry : countries.entrySet()) {
            Country country = entry.getValue();
            country.setSelected(entry.getKey().equals(requestContext.getCountryCode()));
            list.add(country);
        }
        return list;
    }

    public String getCountryCode() {
        return requestContext.getCountryCode();
    }
}
