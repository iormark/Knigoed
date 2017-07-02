package info.knigoed.controller;

import info.knigoed.dao.Invoice;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ShopController {

    @Autowired
    private Invoice invoice;

    @RequestMapping(value = "/shop/{shopId:\\d+}/paying", method = RequestMethod.GET)
    public String shopPaying(@PathVariable int shopId, Model model) throws IOException {
        model.addAttribute("page", "shop-paying");
        return "bundles/index";
    }

}
