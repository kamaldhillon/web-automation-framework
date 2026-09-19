package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import flows.LoginPageFeature;
import utils.RetryAnalyzer;

import java.io.IOException;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginSuccess() throws IOException {
        LoginPageFeature p = new LoginPageFeature();
        p.loginSuccess();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testBrokenUrl() throws IOException {
        LoginPageFeature p = new LoginPageFeature();
        p.checkBrokenUrl();
    }
}
