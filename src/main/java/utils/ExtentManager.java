package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = getReportObject();

    public synchronized static ExtentReports getReportObject() {
        if (extent == null) {
            try {
                System.out.println("Initializing ExtentReports...");
                ExtentSparkReporter reporter = new ExtentSparkReporter("./extentreport/report.html");

                System.out.println("Configuring reporter...");
                reporter.config().setReportName("Test Report");
                reporter.config().setDocumentTitle("Test Results");

                extent = new ExtentReports();
                extent.attachReporter(reporter);
                System.out.println("ExtentReports initialized successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize ExtentReports", e);
            }
        }
        return extent;
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String desc) {
        ExtentTest test = extent.createTest(desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
}
