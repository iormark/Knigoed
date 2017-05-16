package info.knigoed.dao;

import info.knigoed.config.DataSourceConfig;
import info.knigoed.config.RequestContext;
import info.knigoed.config.WebConfig;
import info.knigoed.pojo.InvoicePojo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

//@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = {WebConfig.class, RequestContext.class, DataSourceConfig.class})
public class InvoiceDaoTest {
// 
	@Mock
	private InvoiceDao invoice;

	@Test
	public void testSomeMethod() {
		InvoicePojo invoicePojo = new InvoicePojo();
		invoicePojo.setAmount(200);
		
		//if the author is "mkyong", then return a 'books' object.
		when(invoice.readInvoice(1, 1)).thenReturn(invoicePojo); 

		Assert.assertNotNull(invoice.readInvoice(1, 1));
	}

}
