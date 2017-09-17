package info.knigoed.dao;

import com.google.common.base.Joiner;
import info.knigoed.pojo.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class PriceDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    public List<Price> getPrices(int bookId, String countryCode, String sortPrice, int limit) throws IOException {

        String sql = "SELECT s.shopId, "
            + "s.name, s.domain, s.setting, s.countryCode, p.priceId, p.bookId, "
            + "p.url, p.price, p.currencyCode, p.available, p.downloadable, p.year "
            + "FROM BookPrice p, Shop s "
            + "LEFT JOIN ShopTarget target ON s.shopId=target.shopId "
            + "WHERE s.status IN('pause', 'process') "
            + "AND (target.countryCode = ? OR target.countryCode IS NULL) "
            + "AND p.shopId = s.shopId "
            + "AND p.bookId = ? ORDER BY price ASC LIMIT ?";

        return mysqlJdbc.query(sql, new Object[]{countryCode, bookId, limit}, new BeanPropertyRowMapper(Price.class));
    }

    public List<Price> getPrices(List<Integer> booksId, String countryCode) throws SQLException {
        String join = Joiner.on(",").join(booksId);
        String sql = "SELECT " +
            "p.priceId, p.bookId, p.price, p.url, p.currencyCode, p.year, p.available, p.downloadable, " +
            "s.name, s.domain, s.setting FROM BookPrice p, Shop s LEFT JOIN ShopTarget target ON s.shopId = target.shopId " +
            "WHERE s.status IN('pause', 'process') AND (target.countryCode = ? OR target.countryCode IS NULL) " +
            "AND p.shopId = s.shopId AND p.bookId IN(" + join + ")";

        return mysqlJdbc.query(sql, new Object[]{countryCode}, new BeanPropertyRowMapper(Price.class));
    }
}
