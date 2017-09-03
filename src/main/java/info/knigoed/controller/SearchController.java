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
        @RequestParam String key,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false) Integer shop,
        @RequestParam(required = false) Integer year,
        Model model) throws IOException, SQLException, ClassNotFoundException {

        searchModel(key, keywords, shop, year, model);

        model.addAttribute("bundle", "search");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAjax(
        @RequestParam String key,
        @RequestParam(required = false) String keywords,
        @RequestParam(required = false) Integer shop,
        @RequestParam(required = false) Integer year,
        Model model) throws IOException, SQLException, ClassNotFoundException {

        searchModel(key, keywords, shop, year, model);

        return "bundles/search/search";
    }

    private Model searchModel(String key, String keywords, Integer shop, Integer year, Model model) throws IOException, SQLException {
        long startTime = System.currentTimeMillis();

        // Search
        SearchSphinxParam param = new SearchSphinxParam(key, keywords, shop, year);
        model.addAttribute("key", param.getKey());
        searchService.runSearch(param);
        model.addAttribute("results", searchService.getBooks());
        model.addAttribute("shops", searchService.getShops());

        // Logs
        model.addAttribute("total", searchService.getTotal());
        float time = (float) (System.currentTimeMillis() - startTime) / 1000;
        model.addAttribute("time", time);
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
