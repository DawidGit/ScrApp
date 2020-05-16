package pl.widulinski.scrapp.createExcel;

import org.apache.poi.ss.usermodel.Workbook;
import pl.widulinski.scrapp.FoundWebElements.FoundWebElement;

import java.io.IOException;
import java.util.stream.Stream;

public interface PrintToFile {

    void printExcel (Stream<FoundWebElement> stream, String fileName) throws IOException;
}
