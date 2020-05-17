package pl.widulinski.scrapp.createExcel;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.widulinski.scrapp.FoundWebElements.FoundWebElement;
import pl.widulinski.scrapp.testHelper.ExcelReader;
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
        String fileTestPath = "C:\\Users\\Dawid\\IdeaProjects\\ScrApp\\";
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

        Set<FoundWebElement> foundElements = new HashSet<>();

        for (String[] article : articleList
        ) {
            FoundWebElement element = new FoundWebElement();
            element.setName(article[0]);
            element.setPrice(article[1]);
            element.setLink(article[2]);
            foundElements.add(element);

        }

        Stream<FoundWebElement> streamTest = foundElements.stream();


        //when

        createExcel.printExcel(streamTest, nameFileTest);
        ExcelReader excelReader = new ExcelReader(fileTestPath + fileNameWithDate+ ".xlsx");

        //then

        //comparing article1 data
        Assertions.assertEquals(article1[0], excelReader.getWorkbook().getSheet("WebData").getRow(1).getCell(0).getStringCellValue());
        Assertions.assertEquals(article1[1], excelReader.getWorkbook().getSheet("WebData").getRow(1).getCell(1).getStringCellValue());
        Assertions.assertEquals(article1[2], excelReader.getWorkbook().getSheet("WebData").getRow(1).getCell(2).getStringCellValue());
        //comparing article2 data
        Assertions.assertEquals(article2[0], excelReader.getWorkbook().getSheet("WebData").getRow(2).getCell(0).getStringCellValue());
        Assertions.assertEquals(article2[1], excelReader.getWorkbook().getSheet("WebData").getRow(2).getCell(1).getStringCellValue());
        Assertions.assertEquals(article2[2], excelReader.getWorkbook().getSheet("WebData").getRow(2).getCell(2).getStringCellValue());
        //comparing article3 data
        Assertions.assertEquals(article3[0], excelReader.getWorkbook().getSheet("WebData").getRow(3).getCell(0).getStringCellValue());
        Assertions.assertEquals(article3[1], excelReader.getWorkbook().getSheet("WebData").getRow(3).getCell(1).getStringCellValue());
        Assertions.assertEquals(article3[2], excelReader.getWorkbook().getSheet("WebData").getRow(3).getCell(2).getStringCellValue());

        excelReader.closeWorkbook();
    }


}



