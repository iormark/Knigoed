package info.knigoed.controller;

import info.knigoed.dao.Invoice;
import info.knigoed.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class ShopController {

    @Autowired
    private Invoice invoice;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/shop-list", method = RequestMethod.GET)
    public String shopList(Model model) throws IOException {
        model.addAttribute("shopList", shopService.shopAllByUserId());
        model.addAttribute("bundle", "shop-list");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/shop/{shopId:\\d+}/paying", method = RequestMethod.GET)
    public String shopPaying(@PathVariable int shopId, Model model) throws IOException {
        model.addAttribute("bundle", "shop-paying");
        return "bundles/index";
    }

}
