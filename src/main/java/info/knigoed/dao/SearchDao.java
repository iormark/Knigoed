package info.knigoed.dao;

import info.knigoed.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SearchDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    public List<Shop> getShops(LinkedHashMap<Integer, Integer> shopsId, String countryCode)
        throws SQLException {

        StringBuilder docsId = new StringBuilder();
        docsId.append(shopsId.keySet());
        docsId.deleteCharAt(0);
        docsId.deleteCharAt(docsId.length() - 1);

        String sql = "SELECT s.shopId, s.name, s.domain " +
            "FROM Shop s LEFT JOIN ShopTarget ON s.shopId = ShopTarget.shopId " +
            "WHERE (ShopTarget.countryCode = ? OR ShopTarget.countryCode IS NULL) " +
            "AND status IN('pause','process') AND s.shopId IN(" + docsId + ") " +
            "ORDER BY FIELD(s.shopId, " + docsId + ")";

        return mysqlJdbc.query(sql, new Object[] { countryCode }, new BeanPropertyRowMapper(Shop.class));
    }
}
