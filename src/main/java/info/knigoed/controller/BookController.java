package info.knigoed.controller;

import info.knigoed.pojo.Book;
import info.knigoed.service.BookService;
import info.knigoed.service.CountryService;
import info.knigoed.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private PriceService priceService;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("countryCode", countryService.getCountryCode());
        model.addAttribute("countries", countryService.getCountries());
    }


    @RequestMapping(value = "/book/{bookId:\\d+}", method = RequestMethod.GET)
    public String book(@PathVariable int bookId, Model model) throws Exception {

        model.addAttribute("title", "TODO supply a title ");

        Book book = bookService.getBook(bookId);
        model.addAttribute("book", book);

        model.addAttribute("prices", priceService.getPrices(bookId));

        model.addAttribute("relative", bookService.getBookRelative(book.getIsbnObject(), bookId));

        model.addAttribute("bundle", "book");
        return "bundles/template-1";
    }

}
