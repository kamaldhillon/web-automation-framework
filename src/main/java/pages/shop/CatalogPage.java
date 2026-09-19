package pages.shop;
import java.math.BigDecimal;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
public class CatalogPage extends ShopPage {
    public void addProduct(String slug) { click("add-to-cart-" + slug); }
    public CartPage openCart() {
        clickUsingJavaScript(waitUntilVisible(testId("shopping-cart-link")));
        return new CartPage();
    }
    public void sort(String value) {
        new Select(waitUntilVisible(testId("product-sort-container"))).selectByValue(value);
    }
    public List<BigDecimal> prices() {
        waitUntilVisible(By.className("inventory_item_price"));
        return driver.findElements(By.className("inventory_item_price")).stream()
            .map(e -> new BigDecimal(e.getText().replace("$", ""))).toList();
    }
    public void logout() {
        waitUntilClickable(By.id("react-burger-menu-btn")).click();
        clickUsingJavaScript(waitUntilVisible(testId("logout-sidebar-link")));
        waitUntilVisible(testId("login-button"));
    }
}
