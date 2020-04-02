package pl.widulinski.webcrawlertool.urls;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.widulinski.webcrawlertool.enums.Categories;
import pl.widulinski.webcrawlertool.searchData.DataToSearch;
import pl.widulinski.webcrawlertool.searchData.PreparedDataToSrchDto;
import pl.widulinski.webcrawlertool.searchData.SearchDataService;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrapDto;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrapService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
public class URLController {


    @Autowired
    private DataToScrapService dataToScrapService;

    @Autowired
    private SearchDataService searchDataService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    @GetMapping("/adminMenu")
    public String addShop(Model model) {
        model.addAttribute("data", new DataToScrapDto());
        return "adminMenu";
    }

    @PostMapping("/adminMenu")
    public String addShop(@ModelAttribute("data") @Valid DataToScrapDto dataToScrapDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT HAS ERRORS");

            return "adminMenu";
        } else {

            dataToScrapService.addNewShopParameters(dataToScrapDto);

            return "shopAdded";
        }

    }

    @GetMapping("/")
    public String getUserMenu(Model model) {

        model.addAttribute("shopList", searchDataService.getDataRepository());
        model.addAttribute("result", new DataToSearch());
        return "index";
    }


    @PostMapping("/")
    public String getDataToSearch(@ModelAttribute("result") @Valid PreparedDataToSrchDto preparedDataToSrchDto, BindingResult bindingResult) throws InterruptedException, InvalidFormatException, IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("################################BINDING RESULT HAS ERRORS");


            return bindingResult.getAllErrors().toString();

        } else {

            searchDataService.searchData(preparedDataToSrchDto);

            return "success";

        }


    }

    @RequestMapping(value = "/regions")
    @ResponseBody
    public Set getRegions(@RequestParam String country, Model model) {

        Map<String, Set<Categories>> categories = searchDataService.getDataRepository();

        model.addAttribute("categories", categories.get(country));
        return categories.get(country);
    }
}
