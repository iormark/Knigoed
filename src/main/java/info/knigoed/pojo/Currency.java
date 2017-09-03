package info.knigoed.pojo;

public class Currency {
    private String currencyFrom;
    private String currencyTo;
    private float currencyValue;

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public float getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(float currencyValue) {
        this.currencyValue = currencyValue;
    }
}
