package pl.widulinski.scrapp.webDriver;


import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


@Data
public abstract class DriverBuilder {


    public WebDriver setUpChromeDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
       WebDriver driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        return driver;
    }


}