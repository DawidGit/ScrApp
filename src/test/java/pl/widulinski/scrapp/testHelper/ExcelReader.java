package pl.widulinski.scrapp.testHelper;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Data
public class ExcelReader {

    private final Workbook workbook;
    private final FileInputStream inputStream;

    public ExcelReader(String excelFilePath) throws IOException{

        inputStream = new FileInputStream(new File(excelFilePath));
        workbook = new XSSFWorkbook(inputStream);

        }


    public void closeWorkbook() throws IOException {
        workbook.close();
        inputStream.close();
    }

    public String getCellValue(String sheet, int rowNum, int colNum) {

     return workbook.getSheet(sheet).getRow(rowNum).getCell(colNum).getStringCellValue();
    }
}
