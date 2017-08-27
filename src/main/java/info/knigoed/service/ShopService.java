package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.ShopDao;
import info.knigoed.pojo.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public List<Shop> shopAllByUserId() throws IOException {


        return shopAllByUserIdRewrite(shopDao.shopAllByUserId(1));
    }

    private List<Shop> shopAllByUserIdRewrite(List<Shop> shops) throws IOException {
        List<Shop> list = new ArrayList<>();
        for (Shop shop : shops) {

            list.add(shop);
        }
        LOG.debug("rewriteShops: {}", list);
        return list;
    }

}
