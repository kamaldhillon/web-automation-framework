package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import initialzer.InitPage;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener, org.testng.IExecutionListener, org.testng.IConfigurationListener {
    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.startTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test passed");
        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.getTest().fail(result.getThrowable());
        try {
            String screenshot = ((org.openqa.selenium.TakesScreenshot) initialzer.InitTest.getWebDriver())
                    .getScreenshotAs(org.openqa.selenium.OutputType.BASE64);
            ExtentManager.getTest().fail("Failure screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
        } catch (Exception exception) {
            ExtentManager.getTest().warning("Screenshot unavailable: " + exception.getMessage());
        }
        ExtentManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.startTest(result.getMethod().getMethodName() + " [skipped]", result.getMethod().getDescription());
        ExtentManager.getTest().skip(result.getThrowable() == null ? "Skipped by TestNG" : result.getThrowable().toString());
        ExtentManager.unload();
    }

    @Override
    public void onExecutionFinish() { ExtentManager.getReportObject().flush(); }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        ExtentManager.getReportObject().createTest("Configuration: " + result.getMethod().getMethodName())
                .fail(result.getThrowable());
    }
}
