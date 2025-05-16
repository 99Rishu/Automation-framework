package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.Basetest;

public class Dashboard extends Basetest {
	 private WebDriver driver;

	    public Dashboard(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }
	    
	    @FindBy(xpath="//div[@data-testid='welcome-text']")
	    public WebElement Username; 
	    
	    @FindBy(xpath="//button[@data-testid='close-sidebar-button']" )   
	    public WebElement Sidebarbutton;
	    
	    @FindBy(xpath="//li[@data-testid='menu-item']//span[text()='Assets']")
	    public WebElement Assetsbutton;
	    
	    @FindBy(xpath= "//li[@data-testid='menu-item']//span[text()='Inventory']")
	    public WebElement Inventorybutton;
	    
	    @FindBy(xpath="//li[@data-testid='menu-item']//span[text()='Purchases']")
	    public WebElement Purchasesbutton;
	   
	    @FindBy(xpath="//li[@data-testid='menu-item'] //span[text()='Employees']")
	    public WebElement Employeebutton;
	    
	    @FindBy(xpath="//button[@data-testid='tab-option' and text()='Dashboard'] ")
	    public WebElement Dashboardbutton;
	    
	    @FindBy(xpath="//button[@data-testid='tab-option' and text()='All Assets'] ") 
	    public WebElement AllAssets;
	    
	    @FindBy(xpath="//li[@data-testid='logout-button']")
	    public WebElement Logoutbutton;
	    
	    @FindBy(xpath="//h3[@data-testid='dashboard-card-title' and  text()='Total Assets']")
	    public WebElement Totalassetbox;
	    
	    @FindBy(xpath="//p[@data-testid='dashboard-card-value']")
	    public WebElement AssetCount;
	    
	    @FindBy(xpath="//div[@data-slot='card-title' and text()='All Assets by Group']")
	    public WebElement Assetchart;
	    
	    
	    

}
