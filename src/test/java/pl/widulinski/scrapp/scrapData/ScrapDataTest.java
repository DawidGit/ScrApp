package pl.widulinski.scrapp.scrapData;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.widulinski.scrapp.testHelper.ExcelReader;
import pl.widulinski.scrapp.urls.URLService;
import pl.widulinski.scrapp.webDataToScrap.DataToScrap;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapRepository;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
public class ScrapDataTest{

    @Autowired
    private DataToScrapRepository dataToScrapRepository;

    @Autowired
    private WebDriver browser;



    @Test
    public void testScrapData() throws IOException {

        //given
        URLService urlService = new URLService(dataToScrapRepository,browser);
        Iterable<DataToScrap> areasToScrap = dataToScrapRepository.findByShop("Allegro");
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        //when
        for (DataToScrap area : areasToScrap
        ) {

            urlService.setQuantityOfPages(1);
            urlService.findElement(area.getShop(), area.getCategory());

        }

        //then

        for (DataToScrap area : areasToScrap
        ) {
            ExcelReader excelReader = new ExcelReader(new File(area.getShop() + "_" + area.getCategory() + "_" + currentDate + ".xlsx").getPath());

           Assertions.assertTrue(assertScrappedDataTrue(excelReader));

            }

        }


    boolean assertScrappedDataTrue(ExcelReader excelFile) {

    boolean tempBol = true;

        for (int x = 1; x < excelFile.getWorkbook().getSheet("WebData").getLastRowNum() + 1; x++) {

            String text = excelFile.getCellValue("WebData", x, 2);
            String textFromExcel = text.substring(0, 18);


            if ( excelFile.getCellValue("WebData", x, 0).isEmpty() ||
                    excelFile.getCellValue("WebData", x, 1).isEmpty() ||
                    textFromExcel.equals("https://allegro.pl")) {}
              else{ tempBol = false;}

        }
            return tempBol;
    }


}
