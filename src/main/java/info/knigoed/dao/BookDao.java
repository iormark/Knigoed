package info.knigoed.dao;

import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.util.List;

@Service
public class BookDao {

    @Autowired
    private Sql2o sql2o;

    public Book readBook(int bookId) {
        String sql = "SELECT b.bookId, b.title, b.author, b.publisher, b.series, b.pageExtent, b.binding, "
            + "b.isbn, b.age, b.image, b.edit, b.lastModified, bi.description "
            + "FROM Book b, BookInfo bi WHERE b.bookId = bi.bookId AND b.bookId = :bookId  AND b.edit != 'delete'";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("bookId", bookId)
                .executeAndFetchFirst(Book.class);
        }
    }

      public List<Price> readPrices(int bookId, String countryCode, String sortPrice, int limit) throws IOException {
          
        String sql = "SELECT s.shopId, "
            + "s.name, s.domain, s.setting, s.countryCode, p.priceId, p.bookId, "
            + "p.url, p.price, p.currencyCode, p.available, p.downloadable, p.year "
            + "FROM BookPrice p, Shop s "
            + "LEFT JOIN ShopTarget target ON s.shopId=target.shopId "
            + "WHERE s.status IN('pause', 'process') "
            + "AND (target.countryCode = :countryCode OR target.countryCode IS NULL) "
            + "AND p.shopId = s.shopId "
            + "AND p.bookId = :bookId LIMIT :limit";

        try (Connection con = sql2o.open()) {
            List<Price> prices = con.createQuery(sql).addParameter("countryCode", countryCode)
                .addParameter("bookId", bookId).addParameter("limit", limit).executeAndFetch(Price.class);
            return prices;
        }
    }


}
