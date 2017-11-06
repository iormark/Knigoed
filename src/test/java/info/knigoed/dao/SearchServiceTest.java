package info.knigoed.dao;

import info.knigoed.config.WebConfig;
import info.knigoed.service.SearchService;
import info.knigoed.util.NextPage;
import info.knigoed.util.SearchParam;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, SearchService.class})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;


    @Test
    public void testARunSearch() throws URISyntaxException {
        SearchParam param = new SearchParam("а", "", null, 1, 0);
        NextPage nextPage = new NextPage("df", 1, 100);
        assertTrue(searchService.runSearch(param, nextPage));
    }

    @Test
    public void testGetBooks() throws IOException, SQLException {
        assertFalse(searchService.getBooks().isEmpty());
    }

    @Test
    public void testGetShops() throws IOException, SQLException {
        assertFalse(searchService.getShops().isEmpty());
    }
}
