package info.knigoed.pojo;

import java.util.Date;

public class ShopStatistic {
    private int statisticId;
    private Date creationDate;
    private String clientInfo;
    private String fromUrl;
    private String toUrl;
    private double price;

    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
