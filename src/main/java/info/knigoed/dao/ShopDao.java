package info.knigoed.dao;

import info.knigoed.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.util.List;

@Service
public class ShopDao {

    @Autowired
    private Sql2o sql2o;

    public List<Shop> shopAllByUserId(int userId) throws IOException {

        String sql = "SELECT shopId, name, domain, status, balance, "
            + "(SELECT COUNT(*) FROM ShopStatistic WHERE shopId = s.shopId AND DATE(`creationDate`) = DATE(NOW())) AS clicksToday,"
            + "(SELECT COUNT(*) FROM ShopStatistic WHERE shopId = s.shopId AND DATE(`creationDate`) = DATE(ADDDATE(NOW(), INTERVAL -1 DAY))) AS clicksYesterday,"
            + "(SELECT COUNT(*) FROM ShopStatistic WHERE shopId = s.shopId AND DATE(`creationDate`) >= DATE(ADDDATE(NOW(), INTERVAL -7 DAY)) "
            + "AND DATE(`creationDate`) <= DATE(NOW())) AS clicksWeek "
            + "FROM Shop s WHERE userId = :userId "
            + "GROUP BY shopId ORDER BY shopId ASC";

        try (Connection con = sql2o.open()) {
            List<Shop> prices = con.createQuery(sql).addParameter("userId", userId).executeAndFetch(Shop.class);
            return prices;
        }
    }


}
