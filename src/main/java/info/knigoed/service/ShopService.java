package info.knigoed.service;

import com.google.common.base.Strings;
import info.knigoed.config.RequestContext;
import info.knigoed.dao.ShopDao;
import info.knigoed.pojo.Shop;
import info.knigoed.util.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    private static final Logger LOG = LoggerFactory.getLogger(ShopService.class);

    private final RequestContext requestContext;
    private final ShopDao shopDao;

    @Autowired
    public ShopService(RequestContext requestContext, ShopDao shopDao) {
        this.requestContext = requestContext;
        this.shopDao = shopDao;
    }

    public List<Shop> shopList() throws IOException {
        if (requestContext.hasRole("admin"))
            return shopListRewrite(shopDao.shopAll());
        else
            return shopListRewrite(shopDao.shopAllUserId(requestContext.getUserId()));
    }

    private List<Shop> shopListRewrite(List<Shop> shops) {
        List<Shop> list = new ArrayList<>();
        for (Shop shop : shops) {
            shop.setName(Strings.isNullOrEmpty(shop.getName()) ? shop.getDomain() : shop.getName());
            list.add(shop);
        }
        LOG.debug("rewriteShops: {}", list);
        return list;
    }

    public Shop getShop(int shopId) {
        Shop shop;
        if (requestContext.hasRole("admin"))
            shop = shopDao.getShop(shopId);
        else
            shop = shopDao.getShop(shopId, requestContext.getUserId());
        return shop;
    }


    // ==== Indexing ====
    public Shop getShopIndexing(int shopId) throws EmptyResultDataAccessException, IOException {
        Shop shop = getShop(shopId);
        shop.setSettings(Jackson.mapper().readValue(shop.getSetting(), Shop.Setting.class));
        return shop;
    }

    // ==== Statistic ====
    public Model getShopStatistic(Model model, int shopId, LocalDate startDate, LocalDate endDate) throws EmptyResultDataAccessException, IOException {
        Shop shop = getShop(shopId);
        model.addAttribute("shop", shop);

        if (startDate == null || endDate == null) {
            endDate = LocalDate.now();
            startDate = endDate.minusMonths(1);
        }

        LOG.debug("startDate {}, endDate {}", startDate, endDate);

        model.addAttribute("statistics", shopDao.getShopStatistic(shop.getShopId(), startDate, endDate));
        model.addAttribute("statisticsRows", shopDao.getRows());
        return model;
    }


}
