package base;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final Properties PROPERTIES = loadProperties();

    public static WebDriver getWebDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("WebDriver is not initialized for this thread");
        return driver;
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        String value = systemValue != null && !systemValue.isBlank() ? systemValue : PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Missing framework property: " + key);
        return value.trim();
    }

    public static String getBaseUrl() { return getProperty("baseUrl"); }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        WebDriver driver = createDriver();
        DRIVER.set(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(getProperty("pageLoadTimeoutSeconds"))));
        driver.manage().window().maximize();
        driver.get(getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = DRIVER.get();
        try { if (driver != null) driver.quit(); }
        finally { DRIVER.remove(); }
    }

    public void refreshDriver() { getWebDriver().navigate().refresh(); }

    private static WebDriver createDriver() {
        String browser = System.getProperty("browser", getProperty("browserType")).toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", getProperty("headless")));
        return switch (browser) {
            case "chrome" -> { ChromeOptions o = new ChromeOptions(); if (headless) o.addArguments("--headless=new", "--window-size=1920,1080"); yield new ChromeDriver(o); }
            case "firefox" -> { FirefoxOptions o = new FirefoxOptions(); if (headless) o.addArguments("-headless"); yield new FirefoxDriver(o); }
            case "edge" -> { EdgeOptions o = new EdgeOptions(); if (headless) o.addArguments("--headless=new", "--window-size=1920,1080"); yield new EdgeDriver(o); }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("_web.properties")) {
            if (input == null) throw new IllegalStateException("_web.properties was not found");
            properties.load(input);
            return properties;
        } catch (IOException exception) { throw new ExceptionInInitializerError(exception); }
    }
}
