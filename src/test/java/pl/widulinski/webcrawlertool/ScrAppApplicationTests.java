package pl.widulinski.webcrawlertool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@SpringBootTest
class ScrAppApplicationTests {

    @Autowired
    CreateExcel createExcel;


    private final PreparedDataToSrchDto preparedDataToSrchDto = new PreparedDataToSrchDto();

    @Autowired
    private DataToScrapRepository dataToScrapRepository;

    @Autowired
    private MockMvc mockMvc;

    String pattern = "dd.MM.yyyy";
    String dateInStringTest = new SimpleDateFormat(pattern).format(new Date());
    Set<FoundWebElement> webElementsList = new HashSet<>();
    FoundWebElement webElementTest = new FoundWebElement();
    PreparedDataToSrchDto preparedDataToSearchTest = preparedDataToSrchDto;
    Map<String, DataToScrap> elements = new HashMap<>();
    private WebDriver driver;



    @Test
    @Order(2)
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
    @Order(3)
    public void webDriverTest() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        this.driver = new ChromeDriver(options);
        String baseUrl = "https://www.allegro.pl";
        driver.get(baseUrl);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        if (driver.findElement(By.xpath("//button[text()='przejdź dalej' and @class='_13q9y _8hkto _11eg6 _7qjq4 _ey68j']")).isEnabled()) {
            driver.findElement(By.xpath("//button[text()='przejdź dalej' and @class='_13q9y _8hkto _11eg6 _7qjq4 _ey68j']")).click();
        }

        assertEquals("Allegro - atrakcyjne ceny i 100% bezpieczeństwa", driver.getTitle());  //testing does opened page using webdriver


        driver.close();
    }


    @Test
    @Order(4)
    public void getDataFromRepositoryTest() {


        for (DataToScrap element : dataToScrapRepository.findAll()) {

            elements.put(element.getShop() + "_" + element.getCategory(), element);
        }
        assertFalse(elements.isEmpty());
    }


    @Test
    @Order(5)
    public void getLastPageTest() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        this.driver = new ChromeDriver(options);

        elements.forEach((key, value) -> {

            driver.get(value.getUrlToCategory() + "1");

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            WebElement webElement = driver.findElement(By.xpath(value.getXpathOfLastPage().replace("|", "'")));

            assertTrue(webElement.isEnabled());

        });

        driver.close();
    }

    @Test
    @Order(6)
    public void scrapArticleDataTest() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        this.driver = new ChromeDriver(options);

        DataToScrap foundWebElement = dataToScrapRepository.findByShopAndCategory("Allegro", Categories.ELECTRONICS);

        driver.get(foundWebElement.getUrlToCategory() + "1");



        //replacing signs '|' to '''
        String replacedArticleElement = foundWebElement.getXpathToArticleElement().replace("|", "'");
        String replacedArticleName = foundWebElement.getXpathToArticleName().replace("|", "'");
        String replacedArticleHref = foundWebElement.getXpathToArticleHref().replace("|", "'");
        String replacedArticlePrice = foundWebElement.getXpathToArticlePrice().replace("|", "'");
        //end of replacing

        List<WebElement> webElementsList = driver.findElements(By.xpath(replacedArticleElement));


        for (WebElement element : webElementsList
        ) {

            webElementTest.setName(element.findElement(By.xpath("." + replacedArticleName)).getText());
            webElementTest.setLink(element.findElement(By.xpath("." + replacedArticleHref)).getAttribute("href"));
            webElementTest.setPrice(element.findElement(By.xpath("." + replacedArticlePrice)).getText());

            assertFalse(webElementTest.getName().isEmpty());
            assertFalse(webElementTest.getLink().isEmpty());
            //assertFalse(webElementTest.getPrice().isEmpty());
        }

        driver.close();

    }

    @Test
    @Order(7)
    public void createExcelTest() throws IOException {

        preparedDataToSrchDto.setShop("ShopTest");

        preparedDataToSrchDto.setCategories(Categories.COMPUTERS);


        Stream<FoundWebElement> stream = webElementsList.stream();
        createExcel.createNewFile(stream, preparedDataToSearchTest.getShop(), preparedDataToSrchDto.getCategories());

        File file = new File(preparedDataToSrchDto.getShop() + "_" + preparedDataToSrchDto.getCategories() + "_" + dateInStringTest + ".xlsx");


        assertTrue(file.exists());
    }

}
