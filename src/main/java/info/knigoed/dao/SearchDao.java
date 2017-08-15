package info.knigoed.dao;

import info.knigoed.pojo.Book;
import info.knigoed.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SearchDao {

    @Autowired
    private Sql2o sql2o;

    public List<Book> getBooks(StringBuilder booksId)
        throws SQLException {

        String sql = "SELECT b.bookId, b.title, b.author, b.publisher, f.description " +
            "FROM Book b, BookInfo f WHERE b.bookId = f.bookId "
            + "AND b.bookId IN(" + booksId + ") "
            + "ORDER BY FIELD(b.bookId, " + booksId + ");";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Book.class);
        }
    }


    public List<Shop> getShops(LinkedHashMap<Long, Long> shopsId, String countryCode)
        throws SQLException {

        StringBuilder docsId = new StringBuilder();
        docsId.append(shopsId.keySet());
        docsId.deleteCharAt(0);
        docsId.deleteCharAt(docsId.length() - 1);

        String sql = "SELECT s.shopId, s.name " +
            "FROM Shop s LEFT JOIN ShopTarget ON s.shopId = ShopTarget.shopId " +
            "WHERE (ShopTarget.countryCode = :countryCode OR ShopTarget.countryCode IS NULL) " +
            "AND status IN('pause','process') AND s.shopId IN(" + docsId + ") " +
            "ORDER BY FIELD(s.shopId, " + docsId + ")";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("countryCode", countryCode).executeAndFetch(Shop.class);
        }
    }
}
