package base;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;

import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;
import utils.SeleniumManager;

public class Basetest {
    protected WebDriver driver;
    protected Wait<WebDriver> fluentWait;
    protected WebDriverWait wait;
    
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        ConfigReader.loadProperties();
        new File(System.getProperty("user.dir") + "/test-output/images").mkdirs();
        new File(System.getProperty("user.dir") + "/test-output/screenshots").mkdirs();
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        initializeDriver();
        configureWaits();
        driver.get(ConfigReader.getBaseUrl());
        driver.manage().window().maximize();
    }

    private void initializeDriver() {
        String browser = ConfigReader.getBrowser();
        
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(); 
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
    }
    
    private void configureWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest(result);
        
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());                     
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable().getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            test.log(Status.PASS, "Test Passed",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentReportManager.flushReport();
    }
    
    
      //Clicks on an element with proper wait and error handling
     
    protected void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            ExtentReportManager.getTest().log(Status.INFO, "Clicked on element: " + element.toString());
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to click element: " + e.getMessage());
            Assert.fail("Failed to click element", e);
        }
    }
    
    
      //Waits for an element to be visible
     
    protected WebElement waitForElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Element not visible: " + locator);
            throw e;
        }
    }
    
    
     //Waits for an element to be visible
     
    protected WebElement waitForElementVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Element not visible: " + element);
            throw e;
        }
    }
    
    
     // Waits for an element to be clickable
    
    protected WebElement waitForElementClickable(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Element not clickable: " + element);
            throw e;
        }
    }
  
    
     //Waits for an element to be present in the DOM
     
    protected WebElement waitForElementPresent(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Element not present: " + locator);
            throw e;
        }
    }
    
   
     //Waits for multiple elements to be present in the DOM   
    protected List<WebElement> waitForElementsPresent(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Elements not present: " + locator);
            throw e;
        }
    }
    
    
     // Waits for page to completely load
    protected void waitForPageLoad() {
        try {
            wait.until(driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Page did not load: " + e.getMessage());
            throw e;
        }
    }
}