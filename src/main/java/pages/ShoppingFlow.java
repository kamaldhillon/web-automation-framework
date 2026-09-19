package pages;
import pageobjects.shop.*;
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
        catalog.openCart();
        return new CartPage();
    }
}

