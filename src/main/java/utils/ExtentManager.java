package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();
    private static final ExtentReports EXTENT = createReport();

    private ExtentManager() { }

    private static ExtentReports createReport() {
        try { Files.createDirectories(Path.of("target", "extent-report")); }
        catch (IOException exception) { throw new IllegalStateException("Cannot create report directory", exception); }
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report/index.html");
        reporter.config().setReportName("Selenium UI Regression");
        reporter.config().setDocumentTitle("Automation Results");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        return extent;
    }

    public static ExtentReports getReportObject() { return EXTENT; }
    public static ExtentTest startTest(String name, String description) {
        TEST.set(EXTENT.createTest(name, description));
        return TEST.get();
    }
    public static ExtentTest startTest(String name) {
        return startTest(name, "");
    }
    public static ExtentTest getTest() {
        ExtentTest test = TEST.get();
        if (test == null) throw new IllegalStateException("Extent test is not initialized");
        return test;
    }
    public static void unload() { TEST.remove(); }
}
