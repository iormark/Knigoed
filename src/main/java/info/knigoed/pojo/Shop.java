package info.knigoed.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.HashMap;

public class Shop {

    private int shopId;
    private String shopName;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class Settings {

        //@JsonIgnore
        //HashMap feed, yml_check, report;
        HashMap currencies;

        public HashMap getCurrencies() {
            return currencies;
        }

        public void setCurrencies(HashMap currencies) {
            this.currencies = currencies;
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
