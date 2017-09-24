package info.knigoed.dao;

import info.knigoed.pojo.Shop;
import info.knigoed.pojo.ShopStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ShopDao {
    private static final Logger LOG = LoggerFactory.getLogger(ShopDao.class);
    @Qualifier("mysqlJdbc")
    @Autowired
    private JdbcTemplate mysqlJdbc;

    private String sqlShopAll = "SELECT shopId, name, domain, status, balance, "
        + "(SELECT COUNT(*) FROM ShopStatistic WHERE shopId = s.shopId AND DATE(`creationDate`) = DATE(NOW())) AS clickToday,"
        + "(SELECT COUNT(*) FROM ShopStatistic WHERE shopId = s.shopId AND DATE(`creationDate`) = DATE(ADDDATE(NOW(), INTERVAL -1 DAY))) AS clickYesterday,"
        + "(SELECT COUNT(*) FROM ShopStatistic WHERE shopId = s.shopId AND DATE(`creationDate`) >= DATE(ADDDATE(NOW(), INTERVAL -7 DAY)) "
        + "AND DATE(`creationDate`) <= DATE(NOW())) AS clickWeek FROM Shop s ";

    public List<Shop> shopAllUserId(int userId) throws IOException {
        String sql = sqlShopAll + "WHERE userId = ? GROUP BY shopId ORDER BY shopId ASC";
        return mysqlJdbc.query(sql, new Object[]{userId}, new BeanPropertyRowMapper(Shop.class));
    }

    public List<Shop> shopAll() throws IOException {
        String sql = sqlShopAll + "GROUP BY shopId ORDER BY shopId ASC LIMIT 1000";
        return mysqlJdbc.query(sql, new BeanPropertyRowMapper(Shop.class));
    }


    public Shop getShop(int shopId) {
        String sql = "SELECT shopId, name, domain, status, lastModified, program, balance, setting FROM Shop WHERE shopId = ?";
        return (Shop) mysqlJdbc.queryForObject(sql, new Object[]{shopId}, new BeanPropertyRowMapper(Shop.class));
    }

    public Shop getShop(int shopId, int userId) {
        String sql = "SELECT shopId, name, domain, status, lastModified, program, balance, setting FROM Shop WHERE shopId = ? AND userId = ?";
        return (Shop) mysqlJdbc.queryForObject(sql, new Object[]{shopId, userId}, new BeanPropertyRowMapper(Shop.class));
    }

    // ==== Statistic ====
    public List<ShopStatistic> getShopStatistic(int shopId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT SQL_CALC_FOUND_ROWS statisticId, creationDate, clientInfo, fromUrl, toUrl, price " +
            "FROM ShopStatistic WHERE shopId = ? AND date(creationDate) >= ? AND date(creationDate) <= ?";

        return mysqlJdbc.query(sql, new Object[]{shopId, startDate, endDate}, new BeanPropertyRowMapper(Shop.class));
    }

    public Integer getRows() {
        String sql = "SELECT FOUND_ROWS() AS rows";
        return mysqlJdbc.queryForObject(sql, Integer.class);
    }
}
