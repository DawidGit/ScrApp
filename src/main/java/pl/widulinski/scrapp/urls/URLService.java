package pl.widulinski.scrapp.urls;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import pl.widulinski.scrapp.ScrappedWebElement.ScrappedWebElement;
import pl.widulinski.scrapp.createExcel.CreateExcel;
import pl.widulinski.scrapp.enums.Categories;
import pl.widulinski.scrapp.webDataToScrap.DataToScrap;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapRepository;
import pl.widulinski.scrapp.webDriver.BrowserPicker;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


@Service
@Data
@Slf4j
public class URLService extends BrowserPicker {

    private int quantityOfPages = 0;

    private CreateExcel createExcel;

    private  DataToScrapRepository dataToScrapRepository;


    public void findElement(String shop, Categories category) throws IOException {

        createExcel = new CreateExcel();

        String fileName = shop + "_" + category.getDisplayValue() + "_";

        DataToScrap foundWebElement = dataToScrapRepository.findByShopAndCategory(shop, category);

        //  String pathToCategory, String pathToLastPageNumber

        String firstPageOfCategory = foundWebElement.getUrlToCategory();

        WebDriver driver = setUpChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get(firstPageOfCategory);

        ExpectedCondition<Boolean> expectation = driver1 -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        wait.until(expectation);

        if (foundWebElement.getShop().equals("Allegro")) {
            driver.findElement(By.xpath("//button[text()='przejdź dalej' and @class='_13q9y _8hkto _11eg6 _7qjq4 _ey68j']")).click();
        }

        try {
            if (driver.findElement(By.xpath("//div[@data-analytics-interaction-value='regular']")).isDisplayed()) {
                driver.findElement(By.xpath("//div[@data-analytics-interaction-value='regular']")).click();
                wait.until(expectation);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        log.info("############################# Strona wybranego sklepu załadowana ###########################");


        Set<ScrappedWebElement> foundElements = new HashSet<>();

        //replacing signs '|' to '''
        String replacedLastPage = foundWebElement.getXpathOfLastPage().replace("|", "'");
        String replacedArticleElement = foundWebElement.getXpathToArticleElement().replace("|", "'");
        String replacedArticleName = foundWebElement.getXpathToArticleName().replace("|", "'");
        String replacedArticleHref = foundWebElement.getXpathToArticleHref().replace("|", "'");
        String replacedArticlePrice = foundWebElement.getXpathToArticlePrice().replace("|", "'");
        //end of replacing

        String lastPage = driver.findElement(By.xpath(replacedLastPage)).getText();

        int lastPageParsedToInt;

        if (quantityOfPages == 0) {
            lastPageParsedToInt = Integer.parseInt(lastPage);
        } else {
            lastPageParsedToInt = quantityOfPages;
        }

        log.info("ilość stron w kategorii: " + lastPage);

        for (int i = 1; i <= lastPageParsedToInt; i++) {

            driver.get(firstPageOfCategory + i);

            List<WebElement> webElementsList = driver.findElements(By.xpath(replacedArticleElement));


            for (WebElement element : webElementsList
            ) {

                ScrappedWebElement foundElement = new ScrappedWebElement();

                foundElement.setName(element.findElement(By.xpath("." + replacedArticleName)).getText());
                foundElement.setLink(element.findElement(By.xpath("." + replacedArticleHref)).getAttribute("href"));
                foundElement.setPrice(element.findElement(By.xpath("." + replacedArticlePrice)).getText());

                log.info("Scrap...");
                foundElements.add(foundElement);

            }

        }

        driver.quit();

        Stream<ScrappedWebElement> stream = foundElements.stream();


      createExcel.newWorkbook();
      createExcel.newInsert(stream);
      createExcel.writeToXLS(fileName);

        log.info("Huraaa! znaleziono obiekty");

    }

}