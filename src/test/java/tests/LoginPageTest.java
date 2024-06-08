package tests;

import initialzer.InitTest;
import org.testng.annotations.Test;
import pages.LoginPageFeature;
import utils.ExtentManager;

import java.io.IOException;

public class LoginPageTest extends InitTest {

    @Test
    public void loginSuccess() throws IOException {
        ExtentManager.startTest("Login Success");
        LoginPageFeature p = new LoginPageFeature();
        p.loginSuccess();
    }
}
