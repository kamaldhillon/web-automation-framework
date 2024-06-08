package tests;

import initialzer.InitTest;
import org.testng.annotations.Test;
import pages.LoginPageFeature;
import utils.ExtentManager;
import utils.RetryAnalyzer;

import java.io.IOException;

public class LoginPageTest extends InitTest {

    @Test
    public void loginSuccess() throws IOException {
        ExtentManager.startTest("Login Success");
        LoginPageFeature p = new LoginPageFeature();
        p.loginSuccess();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testBrokenUrl() throws IOException {
        ExtentManager.startTest("Login Success");
        LoginPageFeature p = new LoginPageFeature();
        p.checkBrokenUrl();
    }
}
