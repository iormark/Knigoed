package info.knigoed.controller;

import info.knigoed.dao.Invoice;
import info.knigoed.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class ShopController extends TemplateController {
    @Autowired
    private Invoice invoice;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String shopList(Model model) throws IOException {
        model.addAttribute("title", "");


        model.addAttribute("shopList", shopService.shopList());
        model.addAttribute("bundle", "shop-list");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/shop/{shopId:\\d+}", method = RequestMethod.GET)
    public String shopIndexing(@PathVariable int shopId, Model model) throws IOException {
        model.addAttribute("title", "");
        model.addAttribute("shop", shopService.getShopIndexing(shopId));
        model.addAttribute("bundle", "shop-indexing");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/shop/{shopId:\\d+}/statistic", method = RequestMethod.GET)
    public String shopStatistic(Model model, @PathVariable int shopId,
                                @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws IOException {

        model.addAttribute("title", "");
        model.addAttribute(shopService.getShopStatistic(model, shopId, startDate, endDate));
        model.addAttribute("bundle", "shop-statistic");
        return "bundles/template-1";
    }

    @RequestMapping(value = "/shop/{shopId:\\d+}/paying", method = RequestMethod.GET)
    public String shopPaying(@PathVariable int shopId, Model model) throws IOException {
        model.addAttribute("bundle", "shop-paying");
        return "bundles/index";
    }

}
