package pl.widulinski.scrapp.configuration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.util.Objects;


@Configuration
public class BrowserConfiguration {


    @Bean
    WebDriver createDriver(Environment environment) {

        System.out.println(environment.toString());

        switch (Objects.requireNonNull(environment.getProperty("my.web.driver.name"))) {
            case "chrome":

                System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(environment.getProperty("my.web.driver.path-chrome")));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                WebDriver driver = new ChromeDriver(options);
                driver.manage().deleteAllCookies();
                driver.get("http://www.google.com");
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.elementToBeClickable(By.id("hplogo")));
                return driver;
            case "firefox":
                return new FirefoxDriver();
            case "safari":
                return new SafariDriver();
            default:
                return new EdgeDriver();
        }


    }

}
