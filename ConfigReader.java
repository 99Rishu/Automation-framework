package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import resources.PWEncryption;

public class ConfigReader {
    private static String baseUrl;
    private static String username;
    private static String encryptedPassword;
    private static String browser;
    private static Properties properties;

    public static void loadProperties() {
        properties = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/java/Resources/config.properties")) {
            properties.load(file);
            
            baseUrl = properties.getProperty("BASE_URL", "");
            username = properties.getProperty("USERNAME", "");
            encryptedPassword = properties.getProperty("PASSWORD", "");
            browser = properties.getProperty("BROWSER", "").toLowerCase(); 
            
            if (baseUrl.isEmpty() || username.isEmpty() || browser.isEmpty()) {
                throw new RuntimeException("Missing required properties!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file!", e);
        }
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getUsername() {
        return username;
    }

    public static String getEncryptedPassword() {
        return encryptedPassword;
    }

    public static String getDecryptedPassword() {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new RuntimeException("Encrypted password not found!");
        }
        return PWEncryption.decrypt(encryptedPassword);
    }

    public static String getBrowser() {
        return browser;
    }
}
