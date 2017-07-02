package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.BookDao;
import info.knigoed.exception.ResourceNotFoundException;
import info.knigoed.pojo.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final RequestContext requestContext;
    private final BookDao bookDao;

    @Autowired
    public BookService(RequestContext requestContext, BookDao bookDao) {
        this.requestContext = requestContext;
        this.bookDao = bookDao;
    }

    public Book getBook(int bookId) {

        Book book = bookDao.readBook(bookId);
        if(null == book) {
            throw new ResourceNotFoundException();
        }
  
        if (book.getAge() > -1) {
            book.setAgeValue(book.getAge() + "+");
        }

        return book;
    }

}
