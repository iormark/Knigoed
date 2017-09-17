package info.knigoed.pojo;

public class Country {
    private int countryId;
    private String countryName;
    private String countryCode;
    private String currencyCode;
    private String currencySuffix;
    //
    private boolean selected;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySuffix() {
        return currencySuffix;
    }

    public void setCurrencySuffix(String currencySuffix) {
        this.currencySuffix = currencySuffix;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
