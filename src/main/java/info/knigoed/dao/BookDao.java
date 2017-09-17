package info.knigoed.dao;

import com.google.common.base.Joiner;
import info.knigoed.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BookDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    public Book getBook(int bookId) {
        String sql = "SELECT b.bookId, b.title, b.author, b.publisher, b.series, b.pageExtent, b.binding, "
            + "b.isbn, b.age, b.image, b.edit, b.lastModified, bi.description "
            + "FROM Book b, BookInfo bi WHERE b.bookId = bi.bookId AND b.bookId = ?  AND b.edit != 'delete'";

        return (Book) mysqlJdbc.queryForObject(sql, new Object[]{bookId}, new BeanPropertyRowMapper(Book.class));
    }


    public List<Book> getBooks(List booksId) throws SQLException {
        String join = Joiner.on(",").join(booksId);

        String sql = "SELECT b.bookId, b.author, b.title, b.publisher, b.series, b.isbn, b.image,  f.description " +
            "FROM Book b, BookInfo f WHERE b.bookId = f.bookId "
            + "AND b.bookId IN(" + join + ") "
            + "ORDER BY FIELD(b.bookId, " + join + ");";

        return mysqlJdbc.query(sql, new BeanPropertyRowMapper(Book.class));
    }


    public List<Integer> getBookRelative(String isbn, int ignoreBookId) throws SQLException {

        String sql = "SELECT bookId FROM BookIsbnId WHERE bookId != ? AND isbn IN(" + isbn + ")";

        return mysqlJdbc.queryForList(sql, new Object[]{ignoreBookId}, Integer.class);
    }

}
