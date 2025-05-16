package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import pages.HomePage;
import pages.LoginPage;
import utils.ExtentReportManager;
import utils.TestListener;

import com.aventstack.extentreports.Status;

import base.Basetest;

@Listeners(TestListener.class)
public class HomePageTest extends Basetest {
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        loginPage.login();                   
    }

    @Test(priority = 1)
    public void testFavButton() {
        ExtentReportManager.getTest().log(Status.INFO, "Testing Favorite Button");
        //testButtonInteraction(homePage.FavButton, "Favorite button");
    }

    @Test(priority = 2)
    public void testEditButton() {
        ExtentReportManager.getTest().log(Status.INFO, "Testing Edit Button");
        //testButtonInteraction(homePage.EditButton, "Edit button");
    }

    @Test(priority = 3)
    public void testMenuButton() {
        ExtentReportManager.getTest().log(Status.INFO, "Testing Menu Button");
        //testButtonInteraction(homePage.MenuButton, "Menu button");
    }
}
    
   
