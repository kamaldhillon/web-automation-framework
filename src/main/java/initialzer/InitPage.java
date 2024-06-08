package initialzer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static initialzer.InitTest.getWebDriver;

public class InitPage {
        protected static WebDriver driver;
    protected InitPage(){
        driver= getWebDriver();
        PageFactory.initElements(driver,this);
    }


    public boolean isElementPresent(WebElement element) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

        public boolean waitUntilElementDisappear(String locatorId) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(locatorId)));
            return false;
        } catch (java.util.NoSuchElementException e) {
            return true;
        }
    }

        public boolean isElementClickable(WebElement element){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

        public String waitUsingFluentWait(WebElement webElement, int timeInSeconds) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchFrameException.class);

//        fluentWait.until(new Function<WebDriver, Object>() {
//            @Override
//            public String apply(WebDriver driver) {
//                return webElement.getText();
//            }
//        });
            // Use FluentWait to wait until the WebElement's text is available
            String elementText = fluentWait.until(driver -> {
                String text = webElement.getText();
                return !text.isEmpty() ? text : null; // Return text if not empty, else null
            });
            return elementText;
        }

        public void switchToLatestFrame(){
        driver.switchTo().frame(driver.findElements(By.tagName("iframe")).size() - 1);
    }

        public static String captureScreenshot() throws IOException {
            String filePath="";
        if (driver != null) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            filePath= System.getProperty("user.dir")+"/extentreport/screenshots/"+  System.currentTimeMillis()+".png";
            FileUtils.copyFile(srcFile,new File(filePath));
        }
        else {
            System.out.println("WebDriver instance is not set. Cannot capture screenshot.");
        }
        return filePath;
    }

    protected Set getWindowHandles(){
        Set<String> set =driver.getWindowHandles();
        return set;
    }

    public void switchToWindow(String windowHandle){
        driver.switchTo().window(windowHandle);
    }

    public void acceptAlert(){
        driver.switchTo().alert().accept();
    }

    public void dismissAlert(){
        driver.switchTo().alert().dismiss();
    }

    public void setTextToAlert(String name){
        driver.switchTo().alert().sendKeys(name);
    }

    public void switchToDefaultContent(){
        driver.switchTo().defaultContent();
    }

    public void dragAndDrop(WebElement source, WebElement target){
        Actions action = new Actions(driver);
        action.dragAndDrop(source,target);
        action.build().perform();
    }

    public void rightClick(){
        Actions action = new Actions(driver);
        action.contextClick();
        action.build().perform();
    }

    public void mouseHover(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.build().perform();
    }

    public void sendKeys(String name){
        Actions action = new Actions(driver);
        action.sendKeys(name);
        action.sendKeys(Keys.ENTER).build().perform();
    }

    public void sendKeysKeyboard(WebElement element){
        Actions actions = new Actions(driver);
        actions.click(element).keyDown(Keys.SHIFT).sendKeys("hello").keyUp(Keys.SHIFT).perform();

    }

    public List<WebElement> dropDown(WebElement element){
        Select select= new Select(element);
        select.selectByVisibleText("lam");
        List<WebElement> list=select.getOptions();
        return list;
    }

    public void scrollIntoView(WebElement element){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("argument[0].scrollIntoView(true);",element);
    }

    public void clickToElement(WebElement element){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("argument[0].click();",element);
    }

    public void waitUntilPageLoad(){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("return document.readystate").equals("complete");
    }

    public void highlightElement(WebElement element){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].style.border = '2px solid red'", element);
    }

}
