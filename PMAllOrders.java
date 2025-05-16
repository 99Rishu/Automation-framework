package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Basetest;

public class PMAllOrders extends Basetest {
	 private WebDriver driver;
	 private WebDriverWait wait;

	    public PMAllOrders(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        PageFactory.initElements(driver, this);
	    }

	    @FindBy(xpath="//button[@data-testid='tab-option' and text()='All Orders']")
	    public WebElement AllOrders; 
	    
	    @FindBy(xpath="//button[@data-testid='tab-option' and text()='Pending Approvals']")
	    public WebElement PendingApprovals; 
	    
	    @FindBy(xpath="//button[@data-testid='tab-option' and text()='Completed']")
	    public WebElement Completed; 
	    
	    @FindBy(xpath="//tbody[@data-slot='table-body']/tr")
	    public WebElement PORows;
	    
	    @FindBy(xpath=" //div[@data-testid='column-header' and contains(text(),'Status')]")
	    public WebElement StatusHeader;
	    
	    @FindBy(xpath=" //div[@data-testid='column-header' and contains(text(),'Purchase Number')]")
	    public WebElement PurchaseNumberHeader;
	    
	    @FindBy(xpath=" //div[@data-testid='column-header' and contains(text(),'Requested Date')]")
	    public WebElement RequestedDateHeader;
	    
	    @FindBy(xpath=" //div[@data-testid='column-header' and contains(text(),'Request Total')]")
	    public WebElement RequestTotalHeader;
	    
	    @FindBy(xpath=" //div[@data-testid='column-header' and contains(text(),'Created By')]")
	    public WebElement CreatedByHeader;
	    
	    @FindBy(xpath=" //div[@data-testid='column-header' and contains(text(),'Actions')]")
	    public WebElement ActionsHeader;
	    
	    @FindBy(xpath="//button[@aria-haspopup='dialog' ]")
	    public WebElement DateFilter;
	    
	    @FindBy(xpath="//button[normalize-space(text())='This Year']")
	    public WebElement ThisYearButton;
	    
	    @FindBy(xpath="//button[@data-testid='apply-date' and text()='Apply'] ")
	    public WebElement ApplyButton;
	    
	    @FindBy(xpath="//td[@data-slot='table-cell']")
	    public WebElement Noresultstab;
	
	    
	  
	    
	    
	    
	    
	    
	    
	  

	    
	    
}
	    


