package pages;

import initialzer.InitPage;
import pageobjects.LoginPage;

import java.io.IOException;

public class LoginPageFeature  extends InitPage{

   public void loginSuccess() throws IOException {
      LoginPage page = new LoginPage();
      page.goTo();
      page.validateWindowHandles();
      page.navigateSuccess();
   }
}
