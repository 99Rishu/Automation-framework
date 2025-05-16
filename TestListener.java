package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {       
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.createTest(testName);
    }
    
    
    @Override
    public void onTestSuccess(ITestResult result) {      
    }
    
    @Override
    public void onTestFailure(ITestResult result) {     
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {    
    }
    
    @Override
    public void onFinish(ITestContext context) {       
    }
}