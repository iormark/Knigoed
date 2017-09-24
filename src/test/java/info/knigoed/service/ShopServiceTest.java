package info.knigoed.service;

import info.knigoed.config.DataSourceConfig;
import info.knigoed.config.OtherConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.config.WebConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, OtherConfig.class, DataSourceConfig.class, RequestContext.class})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopServiceTest {
    @Autowired
    private ShopService shopService;



    @Test
    public void testShopStatistic() throws EmptyResultDataAccessException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate toDate = LocalDate.parse("2017-09-21", formatter);
        LocalDate fromDate = toDate.minusMonths(1);
        System.out.println("fromDate " + fromDate);
        System.out.println("toDate " + toDate);
        //shopService.getShopStatistic(null, shopId, );
    }


}