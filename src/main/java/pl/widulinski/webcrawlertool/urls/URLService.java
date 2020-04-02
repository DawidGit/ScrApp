package pl.widulinski.webcrawlertool.urls;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import pl.widulinski.webcrawlertool.FoundWebElements.FoundWebElement;
import pl.widulinski.webcrawlertool.createExcelFiles.CreateExcel;
import pl.widulinski.webcrawlertool.enums.Categories;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrap;
import pl.widulinski.webcrawlertool.webDataToScrap.DataToScrapRepository;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


@Service
@Data
@Slf4j
public class URLService {

    public URLService(CreateExcel createExcel, DataToScrapRepository dataToScrapRepository) {
        this.createExcel = createExcel;
        this.dataToScrapRepository = dataToScrapRepository;
    }


    private CreateExcel createExcel;

    private DataToScrapRepository dataToScrapRepository;


    public void findElement(String shop, Categories category) throws InterruptedException, IOException, InvalidFormatException {


        DataToScrap foundWebElement = dataToScrapRepository.findByShopAndCategory(shop, category);
        //  String pathToCategory, String pathToLastPageNumber
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        String baseUrl = "https://www.google.com";
        driver.get(baseUrl);

        String firstPageOfCategory = foundWebElement.getUrlToCategory();

        driver.get(firstPageOfCategory + "1");

        Thread.sleep(3000);

        if (foundWebElement.getShop().equals("Allegro")) {
            driver.findElement(By.xpath("//button[text()='przejdź dalej' and @class='_13q9y _8hkto _11eg6 _7qjq4 _ey68j']")).click();
        }

        try{
        if (driver.findElement(By.xpath("//div[@data-analytics-interaction-value='regular']")).isDisplayed()){
            driver.findElement(By.xpath("//div[@data-analytics-interaction-value='regular']")).click();
            Thread.sleep(3000); } } catch (NoSuchElementException e) {e.printStackTrace();}

        log.info("############################# Strona wybranego sklepu załadowana ###########################");


        Set<FoundWebElement> foundElements = new HashSet<>();

        //replacing signs '|' to '''
        String replacedLastPage = foundWebElement.getXpathOfLastPage().replace("|", "'");
        String replacedArticleElement = foundWebElement.getXpathToArticleElement().replace("|", "'");
        String replacedArticleName = foundWebElement.getXpathToArticleName().replace("|", "'");
        String replacedArticleHref = foundWebElement.getXpathToArticleHref().replace("|", "'");
        String replacedArticlePrice = foundWebElement.getXpathToArticlePrice().replace("|", "'");
        //end of replacing

        String lastPage = driver.findElement(By.xpath(replacedLastPage)).getText();
        int lastPageParsedToInt = Integer.parseInt(lastPage);

        log.info("ilość stron w kategorii: " + lastPage);

        for (int i = 1; i <= lastPageParsedToInt; i++) {

            driver.get(firstPageOfCategory + i);

            List<WebElement> webElementsList = driver.findElements(By.xpath(replacedArticleElement));


            for (WebElement element : webElementsList
            ) {

                FoundWebElement foundElement = new FoundWebElement();

                foundElement.setName(element.findElement(By.xpath("." + replacedArticleName)).getText());
                foundElement.setLink(element.findElement(By.xpath("." + replacedArticleHref)).getAttribute("href"));
                String pricePath = element.findElement(By.xpath("." + replacedArticlePrice)).getText();
                foundElement.setPrice(pricePath);

                log.info("Scrap...");
                foundElements.add(foundElement);

            }

        }

        driver.close();

        Stream<FoundWebElement> stream = foundElements.stream();

        createExcel.createNewFile(stream, shop, category);


        log.info("Huraaa! znaleziono obiekty");

    }


}
