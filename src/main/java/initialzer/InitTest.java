package initialzer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class InitTest {

        protected static WebDriver driver;

        public static WebDriver getWebDriver() {
        try {
            if (driver == null) {
                initializeDriver();
            }

            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        private static synchronized void initializeDriver() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/_web.properties")) {
            prop.load(fis);

            String browserType = prop.getProperty("browserType");
            String driverPath = prop.getProperty("chromeDriverPath");

            switch (browserType) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + driverPath);
                    ChromeOptions options = new ChromeOptions();
                    driver = new ChromeDriver(options);
//                    WebDriverManager.chromedriver().setup();
                    break;
                case "firefox":
                    break;
                case "safari":
                    break;
                default:
                    break;
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            // Perform operations with the Properties object
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }

        @BeforeSuite(alwaysRun = true)
        public void setup() {
//        ExecLog.setupLogger();

    }

        @AfterMethod(alwaysRun=true)

        public void tearDown()
        {
            driver.close();
        }

        public void refreshDriver(){
        driver.navigate().refresh();
    }
}
