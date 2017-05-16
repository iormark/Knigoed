package info.knigoed.controller;

import info.knigoed.dao.Invoice;
import info.knigoed.pojo.InvoicePojo;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InvoiceController {

	@Autowired
	private Invoice invoice;
	
	@RequestMapping(value = "/invoice", method = RequestMethod.POST)
	public String create(
			@ModelAttribute("Invoice") final InvoicePojo invoicePojo) {

		invoice.createInvoice(invoicePojo.getTargetId(), invoicePojo.getAmount());
		
		return "redirect:/invoice/" + invoicePojo.getInvoiceId();
	}

	@RequestMapping(value = "/invoice/{invoiceId:\\d+}", method = RequestMethod.GET)
	public String shopInvoice(@PathVariable int invoiceId, Model model) throws IOException {

		model.addAttribute("page", "invoice");
		return "bundles/index";
	}
	

}
