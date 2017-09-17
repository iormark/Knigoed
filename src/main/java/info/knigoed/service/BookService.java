package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.BookDao;
import info.knigoed.dao.PriceDao;
import info.knigoed.exception.ResourceNotFoundException;
import info.knigoed.isbn.PrefixRanges;
import info.knigoed.manager.IsbnWrapper;
import info.knigoed.pojo.Book;
import info.knigoed.pojo.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final RequestContext requestContext;
    private final PriceService priceService;
    private final IsbnWrapper isbnWrapper;
    private final BookDao bookDao;
    private final PriceDao priceDao;
    private final PrefixRanges prefixRanges;

    @Autowired
    public BookService(PrefixRanges prefixRanges, RequestContext requestContext, PriceService priceService,
                       IsbnWrapper isbnCheck, BookDao bookDao, PriceDao priceDao) throws Exception {
        this.requestContext = requestContext;
        this.priceService = priceService;
        this.isbnWrapper = isbnCheck;
        this.bookDao = bookDao;
        this.priceDao = priceDao;
        this.prefixRanges = prefixRanges;
    }

    public Book getBook(int bookId) throws Exception {

        Book book = bookDao.getBook(bookId);
        if (null == book) {
            throw new ResourceNotFoundException();
        }

        book.setIsbnObject(isbnWrapper.validation(book));
        LOG.debug("Isbn Readable {}", book.getIsbnObject().getReadable());

        if (book.getAge() != null && book.getAge() > -1) {
            book.setAgeValue(book.getAge() + "+");
        }

        return book;
    }

    /*======== Book Relative ========*/

    public List<Book> getBookRelative(Book.Isbn isbn, int ignoreBookId) {
        if (isbn.isEmpty())
            return new ArrayList<Book>();

        try {
            List<Integer> booksId = bookDao.getBookRelative(isbn.getQuotes(), ignoreBookId);
            LOG.info("Book Relative {}", booksId);

            if (booksId.isEmpty())
                return new ArrayList<Book>();

            HashMap<Integer, TreeSet<Price>> prices = priceService.groupPrices(
                priceDao.getPrices(booksId, requestContext.getCountryCode())
            );

            return rewriteBooks(bookDao.getBooks(booksId), prices);

        } catch (SQLException | IOException e) {
            LOG.error("", e);
        }
        return new ArrayList<Book>();
    }

    private List<Book> rewriteBooks(List<Book> books, HashMap<Integer, TreeSet<Price>> prices) throws IOException {
        List<Book> list = new ArrayList<>();
        for (Book book : books) {
            book.setPrices(prices.get(book.getBookId()));
            list.add(book);
        }
        LOG.debug("rewriteBooks: {}", list);
        return list;
    }
}
