package info.knigoed.dao;

import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
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

    public List<Book> getBooks(StringBuilder booksId) throws SQLException {

        String sql = "SELECT b.bookId, b.author, b.title, b.publisher, b.series, b.isbn, b.image,  f.description " +
            "FROM Book b, BookInfo f WHERE b.bookId = f.bookId "
            + "AND b.bookId IN(" + booksId + ") "
            + "ORDER BY FIELD(b.bookId, " + booksId + ");";

        return mysqlJdbc.query(sql, new BeanPropertyRowMapper(Book.class));
    }

    public List<Price> getPrices(StringBuilder booksId) throws SQLException {

        String sql = "SELECT " +
            "p.priceId, p.bookId, p.price, p.url, p.currencyCode, p.year, p.available, p.downloadable, " +
            "s.name, s.domain, s.setting FROM BookPrice p, Shop s " +
            "WHERE p.shopId = s.shopId AND p.bookId IN(" + booksId + ")";

        return mysqlJdbc.query(sql, new BeanPropertyRowMapper(Price.class));
    }


    public List<Shop> getShops(LinkedHashMap<Integer, Integer> shopsId, String countryCode)
        throws SQLException {

        StringBuilder docsId = new StringBuilder();
        docsId.append(shopsId.keySet());
        docsId.deleteCharAt(0);
        docsId.deleteCharAt(docsId.length() - 1);

        String sql = "SELECT s.shopId, s.name " +
            "FROM Shop s LEFT JOIN ShopTarget ON s.shopId = ShopTarget.shopId " +
            "WHERE (ShopTarget.countryCode = ? OR ShopTarget.countryCode IS NULL) " +
            "AND status IN('pause','process') AND s.shopId IN(" + docsId + ") " +
            "ORDER BY FIELD(s.shopId, " + docsId + ")";

        return mysqlJdbc.query(sql, new Object[] { countryCode }, new BeanPropertyRowMapper(Price.class));
    }
}
