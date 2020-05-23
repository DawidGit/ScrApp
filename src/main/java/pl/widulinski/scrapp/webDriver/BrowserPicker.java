package pl.widulinski.scrapp.webDriver;


import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
@Data
public class BrowserPicker {

    private WebDriver driver;

    public WebDriver setUpChromeDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        return driver;
    }


}