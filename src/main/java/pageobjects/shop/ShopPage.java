package pageobjects.shop;
import initialzer.InitPage;
import org.openqa.selenium.By;
/** Shared browser operations; business assertions belong in tests. */
public abstract class ShopPage extends InitPage {
    protected By testId(String id) { return By.cssSelector("[data-test='" + id + "']"); }
    protected void click(String id) { waitUntilClickable(testId(id)).click(); }
    protected void fill(String id, String value) {
        var field = waitUntilVisible(testId(id)); field.clear(); field.sendKeys(value);
    }
    public String title() { return waitUntilVisible(testId("title")).getText(); }
}

