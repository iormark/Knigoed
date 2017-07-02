package info.knigoed.controller;

import info.knigoed.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class MainController {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(@PathVariable int bookId, Model model) throws IOException {

		model.addAttribute("name", "2TODO supply a title");

		model.addAttribute("book", bookService.getBook(bookId));
		//System.out.println(bookService.getPrices(bookId));

		//model.addAttribute("prices", bookService.getPrices(bookId));

		System.out.println();
		return "bundles/index";
	}

}
