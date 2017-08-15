package info.knigoed.dao;

import info.knigoed.config.DataSourceConfig;
import info.knigoed.config.PropertiesConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.config.WebConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, PropertiesConfig.class, DataSourceConfig.class, RequestContext.class})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchSphinxDaoTest {
    //
    @Autowired
    private SearchSphinxDao searchSphinxDao;

    @Test
    public void testARunSearch() throws SQLException {
        assertTrue(searchSphinxDao.runSearch("а", 0, 10));
    }

    @Test
    public void testGetYears() throws SQLException {
        assertThat(searchSphinxDao.getYears().isEmpty(), is(false));
    }

    @Test
    public void testGetBooksId() throws SQLException {
        assertNotNull(searchSphinxDao.getBooksId());
    }

    @Test
    public void testGetShopsId() throws SQLException {
        assertThat(searchSphinxDao.getShopsId().isEmpty(), is(false));
    }

}
