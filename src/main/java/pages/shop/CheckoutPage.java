package pages.shop;
import java.math.BigDecimal;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
public class CheckoutPage extends ShopPage {
    public void enterCustomer(String first, String last, String postal) {
        fill("firstName", first);
        fill("lastName", last);
        fill("postalCode", postal);
        clickUsingJavaScript(waitUntilVisible(testId("continue")));
        explicitWait().until(ExpectedConditions.urlContains("/checkout-step-two"));
    }
    public String error() { return waitUntilVisible(testId("error")).getText(); }
    private BigDecimal money(String css) {
        return new BigDecimal(waitUntilVisible(By.cssSelector(css)).getText().split("\\$")[1]);
    }
    public BigDecimal subtotal() { return money("[data-test='subtotal-label'], .summary_subtotal_label"); }
    public BigDecimal tax() { return money("[data-test='tax-label'], .summary_tax_label"); }
    public BigDecimal total() { return money("[data-test='total-label'], .summary_total_label"); }
    public String finish() {
        click("finish"); return waitUntilVisible(testId("complete-header")).getText();
    }
}
