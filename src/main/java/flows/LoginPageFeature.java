package flows;

import pages.AmazonLoginPage;

import java.io.IOException;

public class LoginPageFeature {

   public void loginSuccess() throws IOException {
      AmazonLoginPage page = new AmazonLoginPage();
      page.goTo();
      page.validateWindowHandles();
      page.navigateSuccess();
   }

   public void checkBrokenUrl() throws IOException {
      AmazonLoginPage page = new AmazonLoginPage();
      page.validateBrokenUrl("https://www.amazon.com");
   }
}
