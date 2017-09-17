package info.knigoed.dao;

import info.knigoed.config.DataSourceConfig;
import info.knigoed.config.OtherConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.config.WebConfig;
import info.knigoed.exception.ResourceNotFoundException;
import info.knigoed.pojo.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, OtherConfig.class, DataSourceConfig.class, RequestContext.class})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchDaoTest {
    //
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private SearchSphinxDao searchSphinxDao;

    @Test
    public void testARunSearch() throws SQLException {
        if(!searchSphinxDao.runSearch("а", 0, 10))
            throw new ResourceNotFoundException();
    }

    @Test
    public void testGetBooks() throws SQLException {
        //List<Book> books = searchDao.getBooks(searchSphinxDao.getBooksId());
        //assertThat(books.isEmpty(), is(false));
    }

    @Test
    public void testGetShops() throws SQLException {
        List<Shop> shops = searchDao.getShops(searchSphinxDao.getShopsId(), "RU");
        assertThat(shops.isEmpty(), is(false));
    }
}
