package parallelTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.Basetest;
import utils.JsonUtils;
import utils.TestListener;
import utils.WebDriverManagerUtils;


import org.testng.Assert;

public class LoginTest2 extends Basetest {

   
    @Test(dataProvider = "dp")
    void login(String username, String password) throws Exception {
        //WebDriver driver = WebDriverManagerUtils.getDriver();
        driver.get(JsonUtils.getConfigValue("url"));

        driver.findElement(By.id(JsonUtils.getLocator("username"))).sendKeys(username);
        driver.findElement(By.id(JsonUtils.getLocator("password"))).sendKeys(password);

        String act_title = driver.getTitle();
        String exp_title = JsonUtils.getConfigValue("title");

        Assert.assertEquals(act_title, exp_title);
      
    }

    @DataProvider(name = "dp", parallel = true)
    public Object[][] loginData() throws Exception {
        return new Object[][] { JsonUtils.getLoginData()[1] };  // Only second user
    }
}
