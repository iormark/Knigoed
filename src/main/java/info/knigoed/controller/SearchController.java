package info.knigoed.controller;

import info.knigoed.service.SearchService;
import info.knigoed.util.NextPage;
import info.knigoed.util.RequestUtils;
import info.knigoed.util.SearchParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Controller
public class SearchController extends TemplateController {
    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(
        @RequestParam String key,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false, defaultValue = "all") SearchParam.Type type,
        @RequestParam(required = false) Integer shop,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false) boolean ajax,
        HttpServletRequest request,
        Model model) throws IOException, SQLException, ClassNotFoundException, URISyntaxException {

        model = searchModel(key, keywords, type, shop, year, page, request, model);

        if (ajax)
            return "bundles/search/search";

        model.addAttribute("title", key + " - Поиск в Книгоеде");
        model.addAttribute("bundle", "search");
        return "bundles/template-1";
    }

    private Model searchModel(
        String key, String keywords, SearchParam.Type type, Integer shop,
        Integer year, int page, HttpServletRequest request, Model model)
        throws IOException, SQLException, URISyntaxException {

        long startTime = System.currentTimeMillis();
        NextPage nextPage = new NextPage(RequestUtils.getURI(request), page, 10);

        // Search
        SearchParam param = new SearchParam(key, keywords, type, shop, year);
        model.addAttribute("key", param.getKey());
        model.addAttribute("year", param.getYear());
        searchService.runSearch(param, nextPage);
        model.addAttribute("results", searchService.getBooks());
        model.addAttribute("shops", searchService.getShops());

        // Logs
        model.addAttribute("total", searchService.getTotal());
        float time = (float) (System.currentTimeMillis() - startTime) / 1000;
        model.addAttribute("time", time);

        // Next Page
        model.addAttribute("nextPage", nextPage.getNextPage(searchService.getTotal()));
        return model;
    }

    /*@RequestMapping(value = "/search-test", method = RequestMethod.GET)
    public String searchTest(
        @RequestParam("key") String key,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false) Integer shop,
        Model model)
        throws IOException, SQLException, ClassNotFoundException {

        model.addAttribute("name", "Search");

        List<Integer> results = jdbcTemplate.query(
            "SELECT id FROM Book LIMIT 10",
            new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Integer(rs.getInt("id"));
                }
            });
        System.out.println("jdbcTemplate: "+ results);

        model.addAttribute("bundle", "search");
        return "bundles/template-1";
    }*/


}
