package pageobjects;

import initialzer.InitPage;
import initialzer.InitTest;
import java.nio.file.Path;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SeleniumPracticePage extends InitPage {
    private final By result = By.id("result");

    public void open(String path) { driver.navigate().to(InitTest.getBaseUrl() + path); }

    public String login(String username, String password) {
        driver.findElement(By.cssSelector("#username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        return waitUntilVisible(By.cssSelector("#flash")).getText();
    }

    public String waitForDynamicText() {
        driver.findElement(By.cssSelector("#start button")).click();
        waitUntilInvisible(By.id("loading"));
        return waitUsingFluentWait(By.cssSelector("#finish h4"), 10);
    }

    public String enableDynamicInputAndReadValue(String value) {
        driver.findElement(By.cssSelector("#input-example button")).click();
        WebElement input = explicitWait().until(current -> {
            WebElement candidate = current.findElement(By.cssSelector("#input-example input"));
            return candidate.isEnabled() ? candidate : null;
        });
        input.sendKeys(value);
        return input.getDomProperty("value");
    }

    public String selectDropdown(String visibleText) {
        Select select = new Select(driver.findElement(By.id("dropdown")));
        select.selectByVisibleText(visibleText);
        return select.getFirstSelectedOption().getText();
    }

    public String acceptSimpleAlert() {
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Alert']")).click();
        Alert alert = waitForAlert(); String text = alert.getText(); alert.accept(); return text;
    }

    public String dismissConfirmationAlert() {
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")).click();
        dismissAlert(); return waitUntilVisible(result).getText();
    }

    public String enterPrompt(String value) {
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Prompt']")).click();
        Alert alert = waitForAlert(); alert.sendKeys(value); alert.accept(); return waitUntilVisible(result).getText();
    }

    public String editIframe(String text) {
        switchToFrame(By.id("mce_0_ifr"));
        WebElement body = waitUntilVisible(By.id("tinymce"));
        body.clear(); body.sendKeys(text); String actual = body.getText(); switchToDefaultContent(); return actual;
    }

    public String openChildWindowAndReturn() {
        String parent = driver.getWindowHandle();
        driver.findElement(By.linkText("Click Here")).click();
        explicitWait().until(ExpectedConditions.numberOfWindowsToBe(2));
        getWindowHandles().stream().filter(handle -> !handle.equals(parent)).findFirst().ifPresent(this::switchToWindow);
        String childHeading = driver.findElement(By.tagName("h3")).getText();
        driver.close(); switchToWindow(parent); return childHeading;
    }

    public String hoverFirstUser() {
        List<WebElement> figures = driver.findElements(By.cssSelector(".figure"));
        mouseHover(figures.get(0));
        return waitUntilVisible(By.cssSelector(".figure:nth-of-type(1) .figcaption h5")).getText();
    }

    public String rightClickContextArea() {
        rightClick(driver.findElement(By.id("hot-spot")));
        String text = waitForAlert().getText(); acceptAlert(); return text;
    }

    public long useJavaScriptExecutor() {
        waitUntilPageLoad();
        WebElement footer = driver.findElement(By.id("page-footer"));
        scrollIntoView(footer); highlightElement(footer);
        return (Long) javascript().executeScript("return Math.round(window.scrollY);");
    }

    public String getDueAmount(String lastName) {
        return driver.findElement(By.xpath("//table[@id='table1']//td[normalize-space()='" + lastName + "']/following-sibling::td[contains(@class,'dues')]")).getText();
    }

    public String getEmailUsingAncestor(String lastName) {
        return driver.findElement(By.xpath("//table[@id='table1']//td[normalize-space()='" + lastName + "']/ancestor::tr/td[contains(@class,'email')]")).getText();
    }

    public String upload(Path file) {
        driver.findElement(By.id("file-upload")).sendKeys(file.toAbsolutePath().toString());
        driver.findElement(By.id("file-submit")).click();
        return waitUntilVisible(By.id("uploaded-files")).getText();
    }

    public String verifyCookie() {
        driver.manage().addCookie(new Cookie("framework-user", "Kamaljit"));
        return driver.manage().getCookieNamed("framework-user").getValue();
    }
}
