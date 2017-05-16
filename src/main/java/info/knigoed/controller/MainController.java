package info.knigoed.controller;

import info.knigoed.config.RequestContext;
import info.knigoed.service.BookService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@Autowired
	private BookService bookCervice;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(@PathVariable int bookId, Model model) throws IOException {

		model.addAttribute("title", "TODO supply a title");

		model.addAttribute("book", bookCervice.getBook(bookId));
		System.out.println(bookCervice.getPrices(bookId));

		model.addAttribute("prices", bookCervice.getPrices(bookId));

		return "bundles/index";
	}

}
