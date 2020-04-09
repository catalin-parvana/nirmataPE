package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class NirmataCustomWebDriverProvider implements WebDriverProvider {

    private static WebDriver driver;
    private static NirmataApplicationProperties appProperties = new NirmataApplicationProperties();

    private  String url = appProperties.properties.getProperty("url");
    private  String browser = appProperties.properties.getProperty("browser");
    private  String absolutePath = System.getProperty("user.dir");
    private  String osName = System.getProperty("os.name");


    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        Configuration.baseUrl = url;
        Configuration.headless = true;
        Configuration.screenshots = false;
        Configuration.downloadsFolder = absolutePath + "/resources/download/";
        Configuration.reportsFolder = absolutePath + "/build/reports/tests/";
        Configuration.browserSize = "1200x1100";
        Configuration.timeout = 20000;

        switch (browser) {
            case "firefox":
                if (osName.toLowerCase().contains("win")){System.setProperty("webdriver.gecko.driver", absolutePath + "/resources/driver/windows/geckodriver.exe");}
                if (osName.toLowerCase().contains("mac")){System.setProperty("webdriver.gecko.driver", absolutePath + "/resources/driver/mac/geckodriver");}
                else{System.setProperty("webdriver.gecko.driver", absolutePath + "/resources/driver/linux/geckodriver");}
                FirefoxOptions firefoxOptions = new FirefoxOptions()
                        .setHeadless(true)
                        .merge(capabilities);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "opera":
                if (osName.toLowerCase().contains("win")){System.setProperty("webdriver.chrome.driver", absolutePath + "/resources/driver/windows/operadriver.exe");}
                if (osName.toLowerCase().contains("mac")){System.setProperty("webdriver.chrome.driver", absolutePath + "/resources/driver/mac/operadriver");}
                else{System.setProperty("webdriver.chrome.driver", absolutePath + "/resources/driver/linux/operadriver");}
                ChromeOptions operaOptions = new ChromeOptions()
             //           .setHeadless(true)
                        .merge(capabilities);
                driver = new ChromeDriver(operaOptions);
                break;
            default:
                if (osName.toLowerCase().contains("win")){System.setProperty("webdriver.chrome.driver", absolutePath + "/resources/driver/windows/chromedriver.exe");}
                if (osName.toLowerCase().contains("mac")){System.setProperty("webdriver.chrome.driver", absolutePath + "/resources/driver/mac/chromedriver");}
                else{System.setProperty("webdriver.chrome.driver", absolutePath + "/resources/driver/linux/chromedriver");}
                ChromeOptions chromeOptions = new ChromeOptions()
                //        .setHeadless(true)
                        .addArguments("--disable-infobars")
                        .addArguments("--no-sandbox")
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("disable-gpu")
                        .merge(capabilities);
                driver = new EventFiringWebDriver( new ChromeDriver(chromeOptions));
                break;
        }
        return driver;
    }
}
