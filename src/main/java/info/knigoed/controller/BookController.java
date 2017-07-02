package info.knigoed.controller;

import info.knigoed.service.BookService;
import info.knigoed.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private PriceService priceService;


    @RequestMapping(value = "/book/{bookId:\\d+}", method = RequestMethod.GET)
    public String main(@PathVariable int bookId, Model model) throws IOException {

        model.addAttribute("title", "TODO supply a title ");

        model.addAttribute("book", bookService.getBook(bookId));

        model.addAttribute("prices", priceService.getPrices(bookId));

        model.addAttribute("bundle", "book");
        return "bundles/template-1";
    }

}
