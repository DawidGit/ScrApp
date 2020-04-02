package pl.widulinski.webcrawlertool;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.widulinski.webcrawlertool.FoundWebElements.FoundWebElement;
import pl.widulinski.webcrawlertool.createExcelFiles.CreateExcel;
import pl.widulinski.webcrawlertool.enums.Categories;
import pl.widulinski.webcrawlertool.searchData.PreparedDataToSrchDto;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrap;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrapRepository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
class ScrAppApplicationTests {

    @Autowired
    CreateExcel createExcel;

    @Autowired
    private PreparedDataToSrchDto preparedDataToSrchDto;

    @Autowired
    private DataToScrapRepository dataToScrapRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void urlControllerTest() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Select Shop")));

        mockMvc
                .perform(get("/adminMenu"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminMenu"))
                .andExpect(content().string(containsString("WebShop Name")));
    }


    @Test
    public void urlServiceTest() throws Exception {

        //given

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        String baseUrl = "https://www.allegro.pl";
        driver.get(baseUrl);

        Thread.sleep(3000);

        if (driver.findElement(By.xpath("//button[text()='przejdź dalej' and @class='_13q9y _8hkto _11eg6 _7qjq4 _ey68j']")).isEnabled()) {
            driver.findElement(By.xpath("//button[text()='przejdź dalej' and @class='_13q9y _8hkto _11eg6 _7qjq4 _ey68j']")).click();
        }


        //when

        Map<String, DataToScrap> elements = new HashMap<>();

        for (DataToScrap element : dataToScrapRepository.findAll()) {

            elements.put(element.getShop() + element.getCategory() + " " + element.getUrlToCategory(), element);

        }


        //then

        elements.forEach((key, value) -> {


            driver.get(value.getUrlToCategory() + "1");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            WebElement webElement = driver.findElement(By.xpath(value.getXpathOfLastPage().replace("|", "'")));

            assertTrue(webElement.isEnabled());


        });

        driver.close();
    }

    @Test
    public void createExcelTest() throws IOException, InvalidFormatException {

        //given
        Set<FoundWebElement> webElementsList = new HashSet<>();
        FoundWebElement webElementTest = new FoundWebElement();
        PreparedDataToSrchDto preparedDataToSearchTest = preparedDataToSrchDto;
        String pattern = "dd.MM.yyyy";
        String dateInStringTest = new SimpleDateFormat(pattern).format(new Date());

        preparedDataToSrchDto.setShop("ShopTest");

        preparedDataToSrchDto.setCategories(Categories.COMPUTERS);

        webElementTest.setName("ProductTest");
        webElementTest.setLink("www.onet.pl");
        webElementTest.setPrice("1,99");
        webElementsList.add(webElementTest);

        //when
        Stream<FoundWebElement> stream = webElementsList.stream();
        createExcel.createNewFile(stream,preparedDataToSearchTest.getShop(), preparedDataToSrchDto.getCategories());

        File file = new File(preparedDataToSrchDto.getShop() + "_" + preparedDataToSrchDto.getCategories() +"_"+ dateInStringTest + ".xlsx");

        //then

        assertTrue(file.exists());
    }

}
