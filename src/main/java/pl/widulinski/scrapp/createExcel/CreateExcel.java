package pl.widulinski.scrapp.createExcel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pl.widulinski.scrapp.ScrappedWebElement.ScrappedWebElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
public class CreateExcel {

    private final Workbook workbook = new XSSFWorkbook();

    private static final String[] columns = {"name", "price", "link"};

   public void newWorkbook()  {

        // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("WebData");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

    }

   public void newInsert(Stream<ScrappedWebElement> stream) throws IOException {

        AtomicInteger rowNum = new AtomicInteger(1);
        Sheet sheet = workbook.getSheet("WebData");
        stream.forEach(foundWebElement -> {
            Row row = sheet.createRow(rowNum.getAndIncrement());
            row.createCell(0).setCellValue(foundWebElement.getName());
            row.createCell(1).setCellValue(foundWebElement.getPrice());
            row.createCell(2).setCellValue(foundWebElement.getLink());
        });

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    public void writeToXLS(String fileName) throws IOException { // Write the output to a file
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