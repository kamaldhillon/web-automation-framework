package utils;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {
    @Override
    public void onStart(ITestContext context) {

        System.out.println("onStart: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(result.getMethod() + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod() + " test has passed.");
        ExtentManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod() + " test has failed.");
        ExtentManager.getTest().log(Status.FAIL, "Test Failed");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("onFinish: " + context.getName());
        // Do tear down operations for ExtentReports reporting
        ExtentManager.getReportObject().flush();
    }
}
