package utils;

import java.io.File;
import java.util.Properties;

public class SeleniumManager {

    private static SeleniumManager instance;
    private Properties properties;

    private SeleniumManager() {
        properties = new Properties();
    }

    public static SeleniumManager getInstance() {
        if (instance == null) {
            instance = new SeleniumManager();
        }
        return instance;
    }

    public String getDriverPath(String driverName) {
        String os = System.getProperty("os.name").toLowerCase();
        String driverPath = "";

      
        if (os.contains("win")) {
            driverPath = "C:\\Users\\rishmid\\Drivers\\" + driverName + ".exe";  
        } else if (os.contains("mac")) {
            driverPath = "/Users/rishmid/Drivers/" + driverName;  // For MacOS
        } else if (os.contains("nix") || os.contains("nux")) {
            driverPath = "/home/rishmid/Drivers/" + driverName;  // For Linux
        }

        File driverFile = new File(driverPath);
        if (!driverFile.exists()) {
            throw new RuntimeException("Driver not found at: " + driverPath);
        }

        return driverFile.getAbsolutePath();
    }
}
