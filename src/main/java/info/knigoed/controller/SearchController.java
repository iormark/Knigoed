package info.knigoed.controller;

import info.knigoed.service.SearchService;
import info.knigoed.util.SearchSphinxParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(
	    @RequestParam("key") String key,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false) Integer shop,
        Model model)
        throws IOException, SQLException, ClassNotFoundException {

		model.addAttribute("name", "Search" );

        SearchSphinxParam param = new SearchSphinxParam(key, shop, 0, 0);
        searchService.runSearch(param);
		model.addAttribute("books", searchService.getBooks());
		//System.out.println(bookService.getPrices(bookId));

		//model.addAttribute("prices", bookService.getPrices(bookId));

		System.out.println();
        model.addAttribute("bundle", "search");
		return "bundles/index";
	}

}
