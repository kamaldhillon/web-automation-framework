package pages.shop;
import java.util.List;
import org.openqa.selenium.By;
public class CartPage extends ShopPage {
    public CartPage() {
        waitUntilVisible(testId("cart-list"));
    }

    public List<String> productNames() {
        return driver.findElements(By.className("inventory_item_name")).stream()
            .map(e -> e.getText()).toList();
    }
    public void remove(String slug) { click("remove-" + slug); }
    public CheckoutPage checkout() { click("checkout"); return new CheckoutPage(); }
}
