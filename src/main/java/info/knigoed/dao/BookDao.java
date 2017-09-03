package info.knigoed.dao;

import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    public Book readBook(int bookId) {
        String sql = "SELECT b.bookId, b.title, b.author, b.publisher, b.series, b.pageExtent, b.binding, "
            + "b.isbn, b.age, b.image, b.edit, b.lastModified, bi.description "
            + "FROM Book b, BookInfo bi WHERE b.bookId = bi.bookId AND b.bookId = ?  AND b.edit != 'delete'";

        return (Book) mysqlJdbc.queryForObject(sql, new Object[]{bookId}, new BeanPropertyRowMapper(Book.class));
    }

    public List<Price> readPrices(int bookId, String countryCode, String sortPrice, int limit) throws IOException {

        String sql = "SELECT s.shopId, "
            + "s.name, s.domain, s.setting, s.countryCode, p.priceId, p.bookId, "
            + "p.url, p.price, p.currencyCode, p.available, p.downloadable, p.year "
            + "FROM BookPrice p, Shop s "
            + "LEFT JOIN ShopTarget target ON s.shopId=target.shopId "
            + "WHERE s.status IN('pause', 'process') "
            + "AND (target.countryCode = ? OR target.countryCode IS NULL) "
            + "AND p.shopId = s.shopId "
            + "AND p.bookId = ? LIMIT ?";

        return mysqlJdbc.query(sql, new Object[]{countryCode, bookId, limit}, new BeanPropertyRowMapper(Price.class));
    }


}
