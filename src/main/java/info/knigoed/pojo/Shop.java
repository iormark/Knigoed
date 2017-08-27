package info.knigoed.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.HashMap;

public class Shop {

    private int shopId;
    private String name;
    private String domain;
    private Status status = Status.untried;

    public enum Status {
        blocked, pause, process, shortage, untried, not_paid
    }

    private float balance;

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

    // More

    private int clicksToday;
    private int clicksYesterday;
    private int clicksWeek;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getClicksToday() {
        return clicksToday;
    }

    public void setClicksToday(int clicksToday) {
        this.clicksToday = clicksToday;
    }

    public int getClicksYesterday() {
        return clicksYesterday;
    }

    public void setClicksYesterday(int clicksYesterday) {
        this.clicksYesterday = clicksYesterday;
    }

    public int getClicksWeek() {
        return clicksWeek;
    }

    public void setClicksWeek(int clicksWeek) {
        this.clicksWeek = clicksWeek;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
