package pl.widulinski.scrapp.scrapData;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.widulinski.scrapp.testHelper.ExcelReader;
import pl.widulinski.scrapp.urls.URLService;
import pl.widulinski.scrapp.webDataToScrap.DataToScrap;
import pl.widulinski.scrapp.webDataToScrap.DataToScrapRepository;
import pl.widulinski.scrapp.webDriver.DriverBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
public class ScrapDataTest extends DriverBuilder {

    @Autowired
    private DataToScrapRepository dataToScrapRepository;


    @Test
    public void testScrapData() throws IOException {

        //given
        URLService urlService = new URLService(dataToScrapRepository);
        Iterable<DataToScrap> areasToScrap = dataToScrapRepository.findByShop("Allegro");
        String pathToExcel = "C:\\Users\\Dawid\\IdeaProjects\\ScrApp\\";
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
            ExcelReader excelReader = new ExcelReader(pathToExcel + area.getShop() + "_" + area.getCategory() + "_" + currentDate + ".xlsx");

            Workbook workbook = excelReader.getWorkbook();

            for (int x = 1; x < workbook.getSheet("WebData").getLastRowNum() + 1; x++) {
                Assertions.assertFalse(workbook.getSheet("WebData").getRow(x).getCell(0).getStringCellValue().isEmpty()); //name
                Assertions.assertFalse(workbook.getSheet("WebData").getRow(x).getCell(1).getStringCellValue().isEmpty()); //price

                String text = workbook.getSheet("WebData").getRow(x).getCell(2).getStringCellValue();
                String textFromExcel = text.substring(0,18);
                Assertions.assertEquals("https://allegro.pl" , textFromExcel); //link
            }


        }
    }

}
