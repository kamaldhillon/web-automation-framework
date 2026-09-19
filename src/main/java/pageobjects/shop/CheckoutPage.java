package pageobjects.shop;
import java.math.BigDecimal;
import org.openqa.selenium.By;
public class CheckoutPage extends ShopPage {
    public void enterCustomer(String first, String last, String postal) {
        fill("firstName", first); fill("lastName", last); fill("postalCode", postal);
        click("continue");
    }
    public String error() { return waitUntilVisible(testId("error")).getText(); }
    private BigDecimal money(String css) {
        return new BigDecimal(waitUntilVisible(By.cssSelector(css)).getText().split("\\$")[1]);
    }
    public BigDecimal subtotal() { return money(".summary_subtotal_label"); }
    public BigDecimal tax() { return money(".summary_tax_label"); }
    public BigDecimal total() { return money(".summary_total_label"); }
    public String finish() {
        click("finish"); return waitUntilVisible(testId("complete-header")).getText();
    }
}

