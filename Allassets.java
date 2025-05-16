package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Basetest;

public class Allassets extends Basetest {
	 private WebDriver driver;
	 private WebDriverWait wait;

	    public Allassets(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        PageFactory.initElements(driver, this);
	    }
	    
	    @FindBy(xpath="//div[@data-testid='welcome-text'] ")
	    public WebElement Username;
	    
	    @FindBy(xpath="//tbody[@data-slot='table-body']/tr")
	    public WebElement assetRows;

	    @FindBy(xpath = "//input[@data-testid='search-data']")
	    public WebElement Searchbox;
	    
	    @FindBy(xpath = "//button[@data-testid='column-filter']")
	    public WebElement Columnsbutton;
	    
	    @FindBy(xpath="//div[@data-testid='column-header' and text()='Name']")
	    public WebElement Namesorting; 
	    
	    @FindBy(xpath="//div[@data-testid='column-header' and text()='Location']")
	    public WebElement Locationsorting;
	    
	    @FindBy(xpath="//div[@data-testid='column-header' and text()='Group']")
	    public WebElement Groupsorting; 
	    
	    @FindBy(xpath ="//div[@data-testid='filter-button' and preceding-sibling::div[contains(text() ,'Location')]] ")
	    public WebElement Locationfilter;
	    
	    @FindBy(xpath ="//div[@data-testid='filter-button' and preceding-sibling::div[contains(text(),'Status')]]")
	    public WebElement Statusfilter;
	    
	    @FindBy(xpath="//div[@data-testid='filter-button' and preceding-sibling::div[contains(text(), 'Group')]]")
	    public WebElement Groupfilter;
	    
	    @FindBy(xpath="//button[@data-testid='next-grid-page']")
	    public WebElement Nextbutton;
	    
	    @FindBy(xpath="//button[@data-testid='previous-grid-page']")
	    public WebElement Previousbutton;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='Name']")
	    public WebElement Namecolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='Description']")
	    public WebElement Descriptioncolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='Location']")
	    public WebElement Locationcolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='LocationDescription']")
	    public WebElement LocationDescriptioncolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='SerialNumber']")
	    public WebElement SerialNumbercolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='Status']")
	    public WebElement Statuscolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='Group']")
	    public WebElement Groupcolumn;
	    
	    @FindBy(xpath="//div[@role='menuitemcheckbox' and .//text()='Owner']")
	    public WebElement Ownercolumn;
	    
	    @FindBy(xpath="//tr[@data-slot='table-row']")
	    public WebElement Tableheader;
	    

		
	   
}
	    
	    
