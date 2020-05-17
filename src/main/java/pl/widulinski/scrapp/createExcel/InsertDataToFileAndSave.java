package pl.widulinski.scrapp.createExcel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import pl.widulinski.scrapp.FoundWebElements.FoundWebElement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
public abstract class InsertDataToFileAndSave implements PrintToFile {

    void newInsert(Stream<FoundWebElement> stream, Workbook workbook, String fileName) throws IOException {

        AtomicInteger rowNum = new AtomicInteger(1);
        Sheet sheet = workbook.getSheet("WebData");
        stream.forEach(foundWebElement -> {
            Row row = sheet.createRow(rowNum.getAndIncrement());
            row.createCell(0).setCellValue(foundWebElement.getName());
            row.createCell(1).setCellValue(foundWebElement.getPrice());
            row.createCell(2).setCellValue(foundWebElement.getLink());
        });

    writeToXLS(fileName, workbook);

    }

    public void writeToXLS(String fileName, Workbook workbook) throws IOException { // Write the output to a file
        String pattern = "dd.MM.yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        String fileNameWithDate = fileName + dateInString;
        FileOutputStream fileOut = new FileOutputStream(fileNameWithDate + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }


}
