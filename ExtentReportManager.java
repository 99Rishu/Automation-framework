package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.testng.ITestResult;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static Map<String, ExtentTest> testMap = new HashMap<>();
    private static String reportFileName;
    private static final String REPORT_DIR = System.getProperty("user.dir") + "/test-output/";
    private static final String SCREENSHOTS_DIR = REPORT_DIR + "screenshots/";

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            cleanupOldReports();
            
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportFileName = "Automation_Report_" + timestamp + ".html";
            
            new File(REPORT_DIR).mkdirs();
            new File(SCREENSHOTS_DIR).mkdirs();
            
            String reportPath = REPORT_DIR + reportFileName;
                       
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Test Execution Report - " + timestamp);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            
            try {
                String browser = ConfigReader.getBrowser();
                if (browser != null && !browser.isEmpty()) {
                    extent.setSystemInfo("Browser", browser.toUpperCase());
                }
                
                String baseUrl = ConfigReader.getBaseUrl();
                if (baseUrl != null && !baseUrl.isEmpty()) {
                    extent.setSystemInfo("Application URL", baseUrl);
                }
            } catch (Exception e) {
                System.out.println("ExtentReportManager: Could not add some system info: " + e.getMessage());
            }
        }
        return extent;
    }

    private static void cleanupOldReports() {
        try {
            System.out.println("Cleaning up old report files.");
    
            File reportDir = new File(REPORT_DIR);
            if (reportDir.exists()) {
                File[] reportFiles = reportDir.listFiles((dir, name) -> name.endsWith(".html"));
                if (reportFiles != null) {
                    for (File file : reportFiles) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println("Deleted old report: " + file.getName());
                        } else {
                            System.out.println("Failed to delete old report: " + file.getName());
                        }
                    }
                }
            }
            
            File screenshotsDir = new File(SCREENSHOTS_DIR);
            if (screenshotsDir.exists()) {
                File[] screenshotFiles = screenshotsDir.listFiles((dir, name) -> name.endsWith(".png"));
                if (screenshotFiles != null) {
                    for (File file : screenshotFiles) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println("Deleted old screenshot: " + file.getName());
                        } else {
                            System.out.println("Failed to delete old screenshot: " + file.getName());
                        }
                    }
                }
            }
            
            System.out.println("Cleanup completed.");
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    //Create a new ExtentTest
    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = getInstance().createTest(testName);
        testMap.put(testName, test);
        return test;
    }

   

    // Get test by result 
    public static synchronized ExtentTest getTest(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if (testMap.containsKey(methodName)) {
            return testMap.get(methodName);
        }
        return createTest(methodName);
    }
    
    // Get current test
    public static synchronized ExtentTest getTest() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            String methodName = element.getMethodName();
            if (testMap.containsKey(methodName)) {
                return testMap.get(methodName);
            }
        }
        
        // If we can't find a match, return the first test
        if (!testMap.isEmpty()) {
            return testMap.values().iterator().next();
        }
        
        // As a fallback, create a default test
        return createTest("DefaultTest");
    }

    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("ExtentReportManager: Report generated successfully at: " + 
                               System.getProperty("user.dir") + "/test-output/" + reportFileName);
        }
    }
   
    public static void flush() {
        flushReport();
    }
    
    public static String getReportFilePath() {
        return System.getProperty("user.dir") + "/test-output/" + reportFileName;
    }
}