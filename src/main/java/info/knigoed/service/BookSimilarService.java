package info.knigoed.service;

import info.knigoed.config.RequestContext;
import info.knigoed.dao.BookDao;
import info.knigoed.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSimilarService {

	private final RequestContext requestContext;
	private final BookDao bookDao;

	@Autowired
	public BookSimilarService(RequestContext requestContext, BookDao bookDao) {
		this.requestContext = requestContext;
		this.bookDao = bookDao;
	}

	public Book getBook(int bookId) {


		Book book = bookDao.readBook(bookId);
		if (book.getAge() > -1) {
			book.setAgeValue(book.getAge() + "+");
		}

		return book;
	}

}
