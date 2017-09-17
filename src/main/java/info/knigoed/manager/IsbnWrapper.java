package info.knigoed.manager;

import info.knigoed.isbn.ISBNCheck;
import info.knigoed.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IsbnWrapper {
    @Autowired
    private ISBNCheck isbnc;

    public Book.Isbn validation(Book book) throws Exception {
        isbnc.validation(book.getIsbn(), 20);
        Book.Isbn isbn = book.getIsbnObject();
        if (!isbnc.getValid().isEmpty()) {
            isbn.setEmpty(isbnc.getValid().isEmpty());
            isbn.setQuotes(isbnc.getQuotes());
            isbn.setReadable(isbnc.getReadable());
        }
        return isbn;
    }
}
