package pages.shop;
public class SignInPage extends ShopPage {
    public void submit(String username, String password) {
        fill("username", username); fill("password", password); click("login-button");
    }
    public String error() { return waitUntilVisible(testId("error")).getText(); }
}

