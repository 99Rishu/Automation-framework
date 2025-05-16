package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
     
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());       
        String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/";
        
        new File(screenshotDir).mkdirs();
        
        String destinationPath = screenshotDir + screenshotName + "_" + timestamp + ".png";
        String relativePath = "screenshots/" + screenshotName + "_" + timestamp + ".png";

        try {
          
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(destinationPath);
            FileUtils.copyFile(srcFile, destFile);
            
            System.out.println("Screenshot saved: " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }

      
        return relativePath;
    }
}