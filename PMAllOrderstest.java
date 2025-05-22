package tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.Basetest;
import pages.Dashboard;
import pages.LoginPage;
import pages.PMAllOrders;
import utils.ExtentReportManager;
import utils.TestListener;

@Listeners(TestListener.class)
public class PMAllOrderstest extends Basetest {
    private PMAllOrders pmallorders;
    private Dashboard dashboard;
    private LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        loginPage = new LoginPage(driver);
        pmallorders = new PMAllOrders(driver);
        dashboard = new Dashboard(driver);
        loginPage.login();
    }
    
    @Test(priority = 1)
    public void validateCompleteCount() {
        ExtentReportManager.getTest().log(Status.INFO, "Validating Completed Purchase order count");
        
        try {
            // Step 1: Navigate to All Orders tab
            System.out.println("Clicking Purchases button");
            clickElement(dashboard.Purchasesbutton);
            
            System.out.println("Clicking All Orders tab");
            clickElement(pmallorders.AllOrders);
            waitForPageLoad();           
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
            
            // Collect all completed orders across pages
            List<String> allOrdersCompleted = collectCompletedOrders();
            int allOrdersCompletedCount = allOrdersCompleted.size();
            ExtentReportManager.getTest().log(Status.INFO, 
                "Found " + allOrdersCompletedCount + " completed orders in All Orders tab");
            
            
            // Step 2: Navigate to Completed tab and apply filter
            clickElement(pmallorders.Completed);
            waitForPageLoad();         
            // Apply This Year filter
            applyThisYearFilter();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
            
            
            // Collect all orders in Completed tab
            List<String> completedTabOrders = collectAllOrdersInCompletedTab();
            int completedTabCount = completedTabOrders.size();
            ExtentReportManager.getTest().log(Status.INFO, 
                "Found " + completedTabCount + " orders in Completed tab for This Year");
            
            // Step 3: Verify counts match
            Assert.assertEquals(allOrdersCompletedCount, completedTabCount, 
                "Completed orders count mismatch between All Orders (" + allOrdersCompletedCount + ") and Completed tab filtered by This Year (" + completedTabCount + ")");
            
            System.out.println("Counts match in all orders tab and Completed tab");
            ExtentReportManager.getTest().log(Status.PASS, "Completed orders count validation successful");
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            ExtentReportManager.getTest().log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    
    private void applyThisYearFilter() {
        System.out.println("Applying This Year filter");
        try {
            // Click date filter button
            clickElement(pmallorders.DateFilter);
            clickElement(pmallorders.ThisYearButton);
            clickElement(pmallorders.ApplyButton);
            waitForPageLoad();

            
        } catch (Exception e) {
            System.out.println("Failed to apply filter: " + e.getMessage());
            throw e;
        }
    }

    private List<String> collectCompletedOrders() {
        List<String> completedOrders = new ArrayList<>();
        boolean nextPageExists = true;
        int pageCount = 1;
        
        System.out.println("Starting to collect completed orders from All Orders tab");
        
        while (nextPageExists) {       
            // Wait for rows to load
            List<WebElement> currentPageRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//tbody[@data-slot='table-body']/tr")));
 
            // Process each row
            for (int i = 0; i < currentPageRows.size(); i++) {
                try {
                    // Re-locate row to avoid staleness
                    WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//tbody[@data-slot='table-body']/tr)[" + (i+1) + "]")));
                    
                    // Get status cell (2nd column)
                    WebElement statusCell = row.findElement(By.xpath(".//td[2]"));
                    String status = statusCell.getText().trim();
                    
                    // Check if status is "Completed" (case-insensitive)
                    if ("completed".equalsIgnoreCase(status)) {
                        // Get purchase number (1st column)
                        WebElement purchaseNumberCell = row.findElement(By.xpath(".//td[1]"));
                        String poNumber = purchaseNumberCell.getText().trim();
                        completedOrders.add(poNumber);
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            
            // Check for next page
            nextPageExists = navigateToNextPageIfExists(currentPageRows);
            pageCount++;
        }
        
        System.out.println("Total completed orders found in All Orders: " + completedOrders.size());
        return completedOrders;
    }

    private List<String> collectAllOrdersInCompletedTab() {
        List<String> allOrders = new ArrayList<>();
        boolean nextPageExists = true;
        int pageCount = 1;

        //count pages from the begining
        
        while (nextPageExists) {
            
            // Wait for rows to load
            List<WebElement> currentPageRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//tbody[@data-slot='table-body']/tr")));
            
            // Process each row
            for (int i = 0; i < currentPageRows.size(); i++) {
                try {
                    // Re-locate row to avoid staleness
                    WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//tbody[@data-slot='table-body']/tr)[" + (i+1) + "]")));
                    
                    // Get purchase number (1st column)
                    WebElement purchaseNumberCell = row.findElement(By.xpath(".//td[1]"));
                    String poNumber = purchaseNumberCell.getText().trim();
                    allOrders.add(poNumber);
                } catch (Exception e) {
                    continue;
                }
            }

            
            // Check for next page
            nextPageExists = navigateToNextPageIfExists(currentPageRows);
            pageCount++;
        }
        return allOrders;
    }
    

    private boolean navigateToNextPageIfExists(List<WebElement> currentPageRows) {
        try {
            
            List<WebElement> nextButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//button[@data-testid='next-grid-page' and not(@disabled)]")));
            
            if (!nextButtons.isEmpty() && nextButtons.get(0).isEnabled()) {
                WebElement nextButton = nextButtons.get(0);
                nextButton.click();
                
                // Wait for page transition
                wait.until(ExpectedConditions.stalenessOf(currentPageRows.get(0)));
                waitForPageLoad();
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }



@Test(priority = 2)
public void validateSubmittedCount() {
    ExtentReportManager.getTest().log(Status.INFO, "Validating Submitted Purchase order count");
    
    try {
        // Step 1: Navigate to All Orders tab
        System.out.println("Clicking Purchases button");
        clickElement(dashboard.Purchasesbutton);
        
        System.out.println("Clicking All Orders tab");
        clickElement(pmallorders.AllOrders);
        waitForPageLoad();
        
        System.out.println("Waiting for rows to be visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
        
        // Collect all submitted orders across pages
        List<String> allOrdersSubmitted = collectSubmittedOrders();
        int allOrdersSubmittedCount = allOrdersSubmitted.size();
        System.out.println("Submitted orders count: " + allOrdersSubmittedCount);
        ExtentReportManager.getTest().log(Status.INFO,  "Found " + allOrdersSubmittedCount + " submitted orders in All Orders tab");
        
        // Step 2: Navigate to Pending Approvals tab
        System.out.println("Clicking Pending Approvals tab");
        clickElement(pmallorders.PendingApprovals);
        waitForPageLoad();
        
        if (allOrdersSubmittedCount > 0) {
            // If there are submitted orders, count them in Pending Approvals tab
            System.out.println("Waiting for pending approvals rows to be visible");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@data-slot='table-body']/tr")));
            
            
            List<String> pendingApprovalsOrders = collectAllOrdersInPendingApprovals();
            int pendingApprovalsCount = pendingApprovalsOrders.size();
            System.out.println("Order count in pending approvals tab: " + pendingApprovalsCount);
            ExtentReportManager.getTest().log(Status.INFO,"Found " + pendingApprovalsCount + " orders in Pending Approvals tab");
            
            
            // Step 3: Verify counts match
            System.out.println("[Comparing counts - All Orders: " + allOrdersSubmittedCount + " and Pending Approvals: " + pendingApprovalsCount);
            Assert.assertEquals(allOrdersSubmittedCount, pendingApprovalsCount,"Submitted orders count mismatch between All Orders (" + allOrdersSubmittedCount +") and Pending Approvals tab (" + pendingApprovalsCount + ")");
            System.out.println("Counts matched successfully");
            ExtentReportManager.getTest().log(Status.PASS, "Submitted orders count validation successful");
            
            
        } else {
            // Verify no data message and disabled next button when no submitted orders
            System.out.println("Verifying empty state in Pending Approvals");
            verifyNoDataState();
            System.out.println("Verified empty state successfully");
            ExtentReportManager.getTest().log(Status.PASS, "Verified no submitted orders state in Pending Approvals tab");
        }
        
    } catch (Exception e) {
        ExtentReportManager.getTest().log(Status.FAIL, "Test failed: " + e.getMessage());
        throw e;
    }
}







private List<String> collectSubmittedOrders() {
    List<String> submittedOrders = new ArrayList<>();
    boolean nextPageExists = true;
    int pageCount = 1;
    
    System.out.println("Starting to collect submitted orders from All Orders tab");
    
    while (nextPageExists) {
        
        // Wait for rows to load
        List<WebElement> currentPageRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody[@data-slot='table-body']/tr")));
        
        // Process each row
        for (int i = 0; i < currentPageRows.size(); i++) {
            try {
                // Re-locate row to avoid staleness
                WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated( By.xpath("(//tbody[@data-slot='table-body']/tr)[" + (i+1) + "]")));
                
                // Get status cell (2nd column)
                WebElement statusCell = row.findElement(By.xpath(".//td[2]"));
                String status = statusCell.getText().trim();
                
                // Check if status is "Submitted" (case-insensitive)
                if ("submitted".equalsIgnoreCase(status)) {
                    // Get purchase number (1st column)
                    WebElement purchaseNumberCell = row.findElement(By.xpath(".//td[1]"));
                    String poNumber = purchaseNumberCell.getText().trim();
                    submittedOrders.add(poNumber);
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        // Check for next page
        nextPageExists = navigateToNextPageIfExists(currentPageRows);
        pageCount++;
    }
    return submittedOrders;
}

private List<String> collectAllOrdersInPendingApprovals() {
    List<String> allOrders = new ArrayList<>();
    boolean nextPageExists = true;
    int pageCount = 1;
    
    while (nextPageExists) {
        
        // Wait for rows to load
        List<WebElement> currentPageRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody[@data-slot='table-body']/tr")));
        
        // Process each row
        for (int i = 0; i < currentPageRows.size(); i++) {
            try {
                // Re-locate row to avoid staleness
                WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated( By.xpath("(//tbody[@data-slot='table-body']/tr)[" + (i+1) + "]")));
                
                // Get purchase number (1st column)
                WebElement purchaseNumberCell = row.findElement(By.xpath(".//td[1]"));
                String poNumber = purchaseNumberCell.getText().trim();
                allOrders.add(poNumber);
            } catch (Exception e) {
                continue;
            }
        }
        
        // Check for next page
        nextPageExists = navigateToNextPageIfExists(currentPageRows);
        pageCount++;
    }
    
    System.out.println("[Total orders found in Pending Approvals tab: " + allOrders.size());
    return allOrders;
}

private void verifyNoDataState() {
    // Verify no data message is displayed
    WebElement noDataMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//td[@data-slot='table-cell']")));
    
    // Verify next button is disabled
    List<WebElement> nextButtons = driver.findElements(
        By.xpath("//button[@data-testid='next-grid-page' and @disabled]"));
    Assert.assertFalse(nextButtons.isEmpty(), "Next button should be disabled when no data exists");
    System.out.println("[Verified next button is disabled");
}

}
