package utils;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static initialzer.InitPage.captureScreenshot;

public class CustomAssert {

    public static void assertEquals(int actual, int expected, String message) throws IOException {
        try {
            Assert.assertEquals(actual, expected, message);
            String screenshotPath=captureScreenshot();
            ExtentManager.getTest().log(Status.PASS, message).addScreenCaptureFromPath(screenshotPath);
        } catch (AssertionError e) {
            ExtentManager.getTest().log(Status.FAIL, message + ": " + e.getMessage());
            Assert.fail(message + ": " + e.getMessage());
        }
    }

    public static void assertEquals(String actual, String expected, String message) throws IOException {
        try {
            Assert.assertEquals(actual, expected, message);
            String screenshotPath=captureScreenshot();
            ExtentManager.getTest().log(Status.PASS, message).addScreenCaptureFromPath(screenshotPath);
        } catch (AssertionError e) {
            String screenshotPath=captureScreenshot();
            ExtentManager.getTest().log(Status.FAIL, "Actual:" +actual+", Expected: "+expected).addScreenCaptureFromPath(screenshotPath);
            Assert.fail(message + ": " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertTrue(boolean condition, String message) throws IOException {
        try {
            Assert.assertTrue(condition, message);
            String screenshotPath=captureScreenshot();
            ExtentManager.getTest().log(Status.PASS, message).addScreenCaptureFromPath(screenshotPath);
        } catch (AssertionError e) {
            String screenshotPath=captureScreenshot();
            ExtentManager.getTest().log(Status.FAIL, message).addScreenCaptureFromPath(screenshotPath);
            Assert.fail(message + ": " + e.getMessage());
        }
    }
}
