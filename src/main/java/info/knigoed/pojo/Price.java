package info.knigoed.pojo;

import java.util.HashMap;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Price {

	private int shopId;
	private String shopName;
	private String domain;
	private String shopSettings;
	private String country;
	private int rsi;
	private int bookId;
	private String url;
	private double price;
	private String currencyId;
	private String available;
	private String downloadable;
	private int year;
	//
	private HashMap currencies;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getShopSettings() {
		return shopSettings;
	}

	public void setShopSettings(String shopSettings) {
		this.shopSettings = shopSettings;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getRsi() {
		return rsi;
	}

	public void setRsi(int rsi) {
		this.rsi = rsi;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getDownloadable() {
		return downloadable;
	}

	public void setDownloadable(String downloadable) {
		this.downloadable = downloadable;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	// ==
	

	public HashMap getCurrencies() {
		return currencies;
	}

	public void setCurrencies(HashMap currencies) {
		this.currencies = currencies;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
