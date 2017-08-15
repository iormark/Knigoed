package info.knigoed.dao;

import info.knigoed.pojo.InvoicePojo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

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
		
		when(invoice.readInvoice(1, 1)).thenReturn(invoicePojo);

		Assert.assertNotNull(invoice.readInvoice(1, 1));
	}

}
