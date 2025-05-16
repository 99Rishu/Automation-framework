package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "workspace-default")
    public WebElement FavButton;
    
    @FindBy(id = "edit-workspace-layout")
    public WebElement EditButton;

    @FindBy(id = "header-user-menu")
    public WebElement MenuButton;

    
}
