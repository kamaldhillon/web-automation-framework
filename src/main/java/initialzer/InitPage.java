package initialzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class InitPage {
    protected final WebDriver driver;

    protected InitPage() {
        driver = InitTest.getWebDriver();
        PageFactory.initElements(driver, this);
    }

    protected WebDriverWait explicitWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(InitTest.getProperty("explicitWaitSeconds"))));
    }

    public WebElement waitUntilVisible(By locator) { return explicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    public WebElement waitUntilClickable(By locator) { return explicitWait().until(ExpectedConditions.elementToBeClickable(locator)); }
    public boolean waitUntilInvisible(By locator) { return explicitWait().until(ExpectedConditions.invisibilityOfElementLocated(locator)); }
    public boolean isElementPresent(By locator) { return !driver.findElements(locator).isEmpty(); }

    public boolean isElementPresent(WebElement element) {
        try { return explicitWait().until(ExpectedConditions.visibilityOf(element)).isDisplayed(); }
        catch (NoSuchElementException | TimeoutException exception) { return false; }
    }

    public boolean isElementClickable(WebElement element) {
        try { explicitWait().until(ExpectedConditions.elementToBeClickable(element)); return true; }
        catch (TimeoutException exception) { return false; }
    }

    public String waitUsingFluentWait(By locator, int timeoutSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
        return wait.until(current -> { String text = current.findElement(locator).getText(); return text.isBlank() ? null : text; });
    }

    public void switchToFrame(By locator) { explicitWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator)); }
    public void switchToLatestFrame() {
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        if (frames.isEmpty()) throw new IllegalStateException("No iframe is available");
        driver.switchTo().frame(frames.get(frames.size() - 1));
    }
    public void switchToDefaultContent() { driver.switchTo().defaultContent(); }
    public void switchToParentFrame() { driver.switchTo().parentFrame(); }
    public Set<String> getWindowHandles() { return driver.getWindowHandles(); }
    public void switchToWindow(String handle) { driver.switchTo().window(handle); }
    public Alert waitForAlert() { return explicitWait().until(ExpectedConditions.alertIsPresent()); }
    public void acceptAlert() { waitForAlert().accept(); }
    public void dismissAlert() { waitForAlert().dismiss(); }
    public void setTextToAlert(String text) { waitForAlert().sendKeys(text); }
    public void dragAndDrop(WebElement source, WebElement target) { new Actions(driver).dragAndDrop(source, target).perform(); }
    public void rightClick(WebElement element) { new Actions(driver).contextClick(element).perform(); }
    public void doubleClick(WebElement element) { new Actions(driver).doubleClick(element).perform(); }
    public void mouseHover(WebElement element) { new Actions(driver).moveToElement(element).perform(); }
    public void scrollByAmount(int horizontal, int vertical) { new Actions(driver).scrollByAmount(horizontal, vertical).perform(); }
    public void sendKeysWithEnter(String text) { new Actions(driver).sendKeys(text).sendKeys(Keys.ENTER).perform(); }
    public void sendUppercaseText(WebElement element, String text) { new Actions(driver).click(element).keyDown(Keys.SHIFT).sendKeys(text).keyUp(Keys.SHIFT).perform(); }
    public List<WebElement> selectByVisibleText(WebElement element, String text) { Select select = new Select(element); select.selectByVisibleText(text); return select.getOptions(); }
    public List<WebElement> selectByValue(WebElement element, String value) { Select select = new Select(element); select.selectByValue(value); return select.getOptions(); }
    public void scrollIntoView(WebElement element) { javascript().executeScript("arguments[0].scrollIntoView({block:'center'});", element); }
    public void clickUsingJavaScript(WebElement element) { javascript().executeScript("arguments[0].click();", element); }
    public void waitUntilPageLoad() { explicitWait().until(current -> javascript().executeScript("return document.readyState").equals("complete")); }
    public void highlightElement(WebElement element) { javascript().executeScript("arguments[0].style.border='3px solid red';", element); }
    public SearchContext getShadowRoot(By hostLocator) { return driver.findElement(hostLocator).getShadowRoot(); }
    protected JavascriptExecutor javascript() { return (JavascriptExecutor) driver; }

    public static String captureScreenshot() throws IOException {
        WebDriver driver = InitTest.getWebDriver();
        Path directory = Path.of("target", "screenshots");
        Files.createDirectories(directory);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        Path destination = directory.resolve("screenshot_" + timestamp + ".png");
        Files.write(destination, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        return destination.toAbsolutePath().toString();
    }
}
