package tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import pages.Allassets;
import pages.LoginPage;
import utils.ExtentReportManager;
import utils.TestListener;
import pages.Dashboard;

import com.aventstack.extentreports.Status; 
import base.Basetest;

@Listeners(TestListener.class)
public class Assetstest extends Basetest {
    private Allassets allassets;
    private LoginPage loginPage;
    private Dashboard dashboard;

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        loginPage = new LoginPage(driver);
        allassets = new Allassets(driver);
        dashboard = new Dashboard(driver);
        loginPage.login();
    }
       
    @Test(priority = 1)
    public void validateAssetCount() {
        ExtentReportManager.getTest().log(Status.INFO, "Validating asset count between Dashboard and All Assets list");
        
        // Get asset count from the dashboard
        WebElement dashboardCountElement = waitForElementVisible(By.xpath("//p[@data-testid='dashboard-card-value']"));
        String countText = dashboardCountElement.getText().trim();
        int dashboardCount = Integer.parseInt(countText);
        System.out.println("Dashboard Asset Count: " + dashboardCount);

        // Navigate to All Assets page
        waitForElementClickable(dashboard.AllAssets);
        System.out.println("Clicking All Assets button");
        clickElement(dashboard.AllAssets);
        
        waitForPageLoad();
        
        System.out.println("Waiting for rows to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
        System.out.println("Starting to collect all asset rows");
        
        
        // Collect asset rows across all pages
        List<String> assetRows = collectAllAssetRows();
        
        // Assertion
        int totalRowCount = assetRows.size();
        System.out.println("Total row Count: " + totalRowCount);
        Assert.assertEquals(totalRowCount, dashboardCount, "Asset count mismatch between dashboard and grid.");
        
        ExtentReportManager.getTest().log(Status.PASS, "Asset count validation successful");
    }
    
   
     // collect all asset rows across pagination
     
    private List<String> collectAllAssetRows() {
        List<String> assetRows = new ArrayList<>();
        boolean nextPageExists = true;
        

        while (nextPageExists) {
            // Wait for the rows to load and fetch them
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
            List<WebElement> currentPageRows = driver.findElements(By.xpath("//tbody[@data-slot='table-body']/tr"));

            for (WebElement row : currentPageRows) {
                assetRows.add(row.getText());
            }

            // Check if Next button exists and is enabled
            List<WebElement> nextButtons = driver.findElements(By.xpath("//button[@data-testid='next-grid-page']"));
            if (!nextButtons.isEmpty() && nextButtons.get(0).isEnabled()) {
                WebElement nextButton = nextButtons.get(0);
                nextButton.click();

                // Wait for stale or new rows to load
                wait.until(ExpectedConditions.stalenessOf(currentPageRows.get(0)));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
            } else {
                nextPageExists = false;
            }
        }
        return assetRows;
    }


    @Test(priority = 2)
    public void testusername() {
        testElementInteraction(allassets.Username, "Username");
    }
    
    @Test(priority = 3)
    public void testAllAssetButton() {
        waitForElementPresent(By.xpath("//button[@data-testid='tab-option' and text()='All Assets']"));
        testElementInteraction(dashboard.AllAssets, "All Assets");
    }
   
    @Test(priority = 4)
    public void testnamesorting() {
        testElementInteraction(allassets.Namesorting, "Namesorting");
    }
    
    @Test(priority = 5)
    public void testlocationsorting() {
        testElementInteraction(allassets.Locationsorting, "Locationsorting");
    }
    
    @Test(priority = 6)
    public void testgrpupsorting() {
        testElementInteraction(allassets.Groupsorting, "Groupsorting");
    }
    
    @Test(priority = 7)
    public void testlocationfilter() {
    	waitForElementClickable(allassets.Locationfilter);
        testElementInteraction(allassets.Locationfilter, "Locationfilter");
    }
    
    @Test(priority = 8)
    public void teststatusfilter() {
    	waitForElementClickable(allassets.Statusfilter);
        testElementInteraction(allassets.Statusfilter, "Statusfilter");
    }
    
    @Test(priority = 9)
    public void testgroupfilter() {
    	waitForElementClickable(allassets.Groupfilter);
        testElementInteraction(allassets.Groupfilter,"Groupfilter");
    }
    
    @Test(priority = 10)
    public void testnextbutton() {
        testElementInteraction(allassets.Nextbutton, "Nextbutton");
    }
    
    @Test(priority = 11)
    public void testpreviousbutton() {
  
        testElementInteraction(allassets.Nextbutton, "Nextbutton");
        waitForElementClickable(allassets.Previousbutton);
        
        testElementInteraction(allassets.Previousbutton, "Previousbutton");
    }
    
    @Test(priority = 12)
    public void testsearchbox() {
        testElementInteraction(allassets.Searchbox, "Searchbox");
    }
     
    @Test(priority = 13)
    public void testcolumnfilter() {
        testElementInteraction(allassets.Columnsbutton, "Columnsbutton");
    }
    
    
   
    private void testElementInteraction(WebElement element, String elementName) {
        ExtentReportManager.getTest().log(Status.INFO, "Testing interaction with " + elementName);
        clickElement(element);
        ExtentReportManager.getTest().log(Status.PASS, "Successfully interacted with: " + elementName);
    }
}