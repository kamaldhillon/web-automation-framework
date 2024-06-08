package pageobjects;

import initialzer.InitPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CustomAssert;

import java.io.IOException;
import java.util.Set;

public class LoginPage extends InitPage {
    public LoginPage() {
        super();
    }

    @FindBy(xpath = "//a[@aria-label='Amazon']")
    WebElement homePageLbl;

    @FindBy(id="email_mobile_login")
    WebElement emailTxt;

    @FindBy(id="password_login")
    WebElement passwordTxt;

    @FindBy(id="login_button")
    WebElement loginBtn;

    @FindBy(css = "a[id='nav-logo-sprites']")
    WebElement homeLbl;
    public void goTo(){
        driver.get("https://www.amazon.com");
    }

    public void loginToApplication(String email, String password){
        emailTxt.sendKeys(email);
        passwordTxt.sendKeys(password);
        loginBtn.click();
    }

    public void navigateSuccess() throws IOException {
        isElementPresent(homePageLbl);
        highlightElement(homePageLbl);
        CustomAssert.assertTrue(homePageLbl.isDisplayed(),"Navigated to home page");
    }

    public void validateWindowHandles(){
        Set<String> set= getWindowHandles();
        set.forEach(e->System.out.println(e));
    }
}
