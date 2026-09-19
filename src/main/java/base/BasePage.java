package base;

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

public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage() {
        driver = BaseTest.getWebDriver();
        PageFactory.initElements(driver, this);
    }

    protected WebDriverWait explicitWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(BaseTest.getProperty("explicitWaitSeconds"))));
    }

    protected WebElement waitUntilVisible(By locator) { return explicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    protected WebElement waitUntilClickable(By locator) { return explicitWait().until(ExpectedConditions.elementToBeClickable(locator)); }
    protected boolean waitUntilInvisible(By locator) { return explicitWait().until(ExpectedConditions.invisibilityOfElementLocated(locator)); }
    protected boolean isElementPresent(By locator) { return !driver.findElements(locator).isEmpty(); }

    protected boolean isElementPresent(WebElement element) {
        try { return explicitWait().until(ExpectedConditions.visibilityOf(element)).isDisplayed(); }
        catch (NoSuchElementException | TimeoutException exception) { return false; }
    }

    protected boolean isElementClickable(WebElement element) {
        try { explicitWait().until(ExpectedConditions.elementToBeClickable(element)); return true; }
        catch (TimeoutException exception) { return false; }
    }

    protected String waitUsingFluentWait(By locator, int timeoutSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
        return wait.until(current -> { String text = current.findElement(locator).getText(); return text.isBlank() ? null : text; });
    }

    protected void switchToFrame(By locator) { explicitWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator)); }
    protected void switchToLatestFrame() {
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        if (frames.isEmpty()) throw new IllegalStateException("No iframe is available");
        driver.switchTo().frame(frames.get(frames.size() - 1));
    }
    protected void switchToDefaultContent() { driver.switchTo().defaultContent(); }
    protected void switchToParentFrame() { driver.switchTo().parentFrame(); }
    protected Set<String> getWindowHandles() { return driver.getWindowHandles(); }
    protected void switchToWindow(String handle) { driver.switchTo().window(handle); }
    protected Alert waitForAlert() { return explicitWait().until(ExpectedConditions.alertIsPresent()); }
    protected void acceptAlert() { waitForAlert().accept(); }
    protected void dismissAlert() { waitForAlert().dismiss(); }
    protected void setTextToAlert(String text) { waitForAlert().sendKeys(text); }
    protected void dragAndDrop(WebElement source, WebElement target) { new Actions(driver).dragAndDrop(source, target).perform(); }
    protected void rightClick(WebElement element) { new Actions(driver).contextClick(element).perform(); }
    protected void doubleClick(WebElement element) { new Actions(driver).doubleClick(element).perform(); }
    protected void mouseHover(WebElement element) { new Actions(driver).moveToElement(element).perform(); }
    protected void scrollByAmount(int horizontal, int vertical) { new Actions(driver).scrollByAmount(horizontal, vertical).perform(); }
    protected void sendKeysWithEnter(String text) { new Actions(driver).sendKeys(text).sendKeys(Keys.ENTER).perform(); }
    protected void sendUppercaseText(WebElement element, String text) { new Actions(driver).click(element).keyDown(Keys.SHIFT).sendKeys(text).keyUp(Keys.SHIFT).perform(); }
    protected List<WebElement> selectByVisibleText(WebElement element, String text) { Select select = new Select(element); select.selectByVisibleText(text); return select.getOptions(); }
    protected List<WebElement> selectByValue(WebElement element, String value) { Select select = new Select(element); select.selectByValue(value); return select.getOptions(); }
    protected void scrollIntoView(WebElement element) { javascript().executeScript("arguments[0].scrollIntoView({block:'center'});", element); }
    protected void clickUsingJavaScript(WebElement element) { javascript().executeScript("arguments[0].click();", element); }
    protected void waitUntilPageLoad() { explicitWait().until(current -> javascript().executeScript("return document.readyState").equals("complete")); }
    protected void highlightElement(WebElement element) { javascript().executeScript("arguments[0].style.border='3px solid red';", element); }
    protected SearchContext getShadowRoot(By hostLocator) { return driver.findElement(hostLocator).getShadowRoot(); }
    protected JavascriptExecutor javascript() { return (JavascriptExecutor) driver; }

    public static String captureScreenshot() throws IOException {
        WebDriver driver = BaseTest.getWebDriver();
        Path directory = Path.of("target", "screenshots");
        Files.createDirectories(directory);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        Path destination = directory.resolve("screenshot_" + timestamp + ".png");
        Files.write(destination, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        return destination.toAbsolutePath().toString();
    }
}
