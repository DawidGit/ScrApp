package pl.widulinski.scrapp.createExcel;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.widulinski.scrapp.ScrappedWebElement.ScrappedWebElement;
import pl.widulinski.scrapp.testHelper.ExcelReader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;


@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
public class CreateExcelTests {


    @Test
    public void testCreateExcel() throws IOException {
        //given
        CreateExcel createExcel = new CreateExcel();
        String nameFileTest = "AllegroTest_Electronics_";
        String dateInString = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        String fileNameWithDate = nameFileTest + dateInString;

        ArrayList<String[]> articleList = new ArrayList<String[]>();

        String[] article1 = new String[]{"article name1", "9,99", "www.testsite1.op.pl"};
        String[] article2 = new String[]{"article name2", "19,99", "www.testsite2.op.pl"};
        String[] article3 = new String[]{"article name3", "29,99", "www.testsite3.op.pl"};

        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);

        Set<ScrappedWebElement> foundElements = new HashSet<>();

        for (String[] article : articleList
        ) {
            ScrappedWebElement element = new ScrappedWebElement();
            element.setName(article[0]);
            element.setPrice(article[1]);
            element.setLink(article[2]);
            foundElements.add(element);

        }

        Stream<ScrappedWebElement> streamTest = foundElements.stream();


        //when

        createExcel.newWorkbook();
        createExcel.newInsert(streamTest);
        createExcel.writeToXLS(nameFileTest);


        ExcelReader excelReader = new ExcelReader(new File(fileNameWithDate+ ".xlsx").getPath());

        //then

        //comparing article1 data
        Assertions.assertEquals(article1[0], excelReader.getCellValue("WebData",1,0));
        Assertions.assertEquals(article1[1], excelReader.getCellValue("WebData",1,1));
        Assertions.assertEquals(article1[2], excelReader.getCellValue("WebData",1,2));

        //comparing article2 data
        Assertions.assertEquals(article2[0], excelReader.getCellValue("WebData",2,0));
        Assertions.assertEquals(article2[1], excelReader.getCellValue("WebData",2,1));
        Assertions.assertEquals(article2[2], excelReader.getCellValue("WebData",2,2));

        //comparing article3 data
        Assertions.assertEquals(article3[0], excelReader.getCellValue("WebData",3,0));
        Assertions.assertEquals(article3[1], excelReader.getCellValue("WebData",3,1));
        Assertions.assertEquals(article3[2], excelReader.getCellValue("WebData",3,2));

        excelReader.closeWorkbook();
    }


}



