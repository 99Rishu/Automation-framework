package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ConfigReader;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "login-username")
    private WebElement usernameField;

    @FindBy(id = "login-password")
    private WebElement passwordField;

    @FindBy(id = "signin-button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login() {
        usernameField.sendKeys(ConfigReader.getUsername());
        passwordField.sendKeys(ConfigReader.getDecryptedPassword());
        loginButton.click();
    }
}
