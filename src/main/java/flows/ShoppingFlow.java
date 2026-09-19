package flows;
import pages.shop.*;
/** Business flow orchestrates pages, without assertions or test dependencies. */
public class ShoppingFlow {
    public CatalogPage signIn() {
        new SignInPage().submit(
            System.getenv().getOrDefault("SHOP_USER", "standard_user"),
            System.getenv().getOrDefault("SHOP_PASSWORD", "secret_sauce"));
        CatalogPage catalog = new CatalogPage();
        catalog.title(); // wait for destination page
        return catalog;
    }
    public CartPage addBackpackToCart() {
        CatalogPage catalog = signIn();
        catalog.addProduct("sauce-labs-backpack");
        return catalog.openCart();
    }
}
