package info.knigoed.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;
import java.util.HashMap;

public class Shop {

    private int shopId;
    private String name;
    private String domain;
    private Status status = Status.untried;

    public enum Status {
        blocked, pause, process, shortage, untried, not_paid
    }

    private Date lastModified;
    private float balance;
    private String setting;
    private Setting settings;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class Setting {

        //@JsonIgnore
        HashMap feed, yml_check, report, currencies;

        public HashMap getFeed() {
            return feed;
        }

        public void setFeed(HashMap feed) {
            this.feed = feed;
        }

        public HashMap getYml_check() {
            return yml_check;
        }

        public void setYml_check(HashMap yml_check) {
            this.yml_check = yml_check;
        }

        public HashMap getReport() {
            return report;
        }

        public void setReport(HashMap report) {
            this.report = report;
        }

        public HashMap getCurrencies() {
            return currencies;
        }

        public void setCurrencies(HashMap currencies) {
            this.currencies = currencies;
        }
    }

    // More
    private int count;
    private int clickToday;
    private int clickYesterday;
    private int clickWeek;

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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public Setting getSettings() {
        return settings;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getClickToday() {
        return clickToday;
    }

    public void setClickToday(int clicksToday) {
        this.clickToday = clicksToday;
    }

    public int getClickYesterday() {
        return clickYesterday;
    }

    public void setClickYesterday(int clicksYesterday) {
        this.clickYesterday = clicksYesterday;
    }

    public int getClickWeek() {
        return clickWeek;
    }

    public void setClickWeek(int clicksWeek) {
        this.clickWeek = clicksWeek;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
