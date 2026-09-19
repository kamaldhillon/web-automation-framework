package tests.business;
import initialzer.InitTest;
import pages.ShoppingFlow;
import pageobjects.shop.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ShoppingJourneyTest extends InitTest {
    @Test(groups={"smoke","regression"}, description="Customer buys a backpack and verifies checkout totals")
    public void customerCompletesPurchase() {
        CartPage cart = new ShoppingFlow().addBackpackToCart();
        Assert.assertEquals(cart.productNames(), java.util.List.of("Sauce Labs Backpack"));
        CheckoutPage checkout = cart.checkout();
        checkout.enterCustomer("Kamaljit", "Kaur", "122001");
        SoftAssert checks = new SoftAssert();
        checks.assertEquals(checkout.subtotal(), new BigDecimal("29.99"), "Backpack subtotal");
        checks.assertEquals(checkout.total(), checkout.subtotal().add(checkout.tax()), "Total calculation");
        checks.assertAll(); // do not complete order if financial checks fail
        Assert.assertEquals(checkout.finish(), "Thank you for your order!");
    }

    @Test(groups="regression", description="Customer removes a product before checkout")
    public void customerRemovesProduct() {
        CartPage cart = new ShoppingFlow().addBackpackToCart();
        cart.remove("sauce-labs-backpack");
        Assert.assertTrue(cart.productNames().isEmpty(), "Cart must be empty");
    }

    @Test(groups="regression", description="Customer sorts inventory by ascending price")
    public void catalogSortsByPrice() {
        CatalogPage catalog = new ShoppingFlow().signIn();
        catalog.sort("lohi");
        var actual = catalog.prices();
        var expected = new ArrayList<>(actual); Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name="missingCustomerFields")
    public Object[][] missingCustomerFields() {
        return new Object[][]{
            {"", "Kaur", "122001", "First Name is required"},
            {"Kamaljit", "", "122001", "Last Name is required"},
            {"Kamaljit", "Kaur", "", "Postal Code is required"}
        };
    }
    @Test(groups="regression", dataProvider="missingCustomerFields")
    public void checkoutRejectsMissingRequiredData(String first, String last, String postal, String expected) {
        CheckoutPage checkout = new ShoppingFlow().addBackpackToCart().checkout();
        checkout.enterCustomer(first, last, postal);
        Assert.assertTrue(checkout.error().contains(expected));
    }

    @Test(groups={"smoke","regression"})
    public void customerLogsOut() {
        new ShoppingFlow().signIn().logout();
        Assert.assertTrue(getWebDriver().getCurrentUrl().endsWith("/"));
    }

    @Test(groups="regression")
    public void lockedCustomerCannotLogin() {
        new SignInPage().submit("locked_out_user", "secret_sauce");
        Assert.assertTrue(new SignInPage().error().contains("locked out"));
    }
}

