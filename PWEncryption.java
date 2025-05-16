package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import utils.EncryptionUtils;

public class PWEncryption {
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the password to encrypt: ");
        String plainPassword = scanner.nextLine();
        String encryptedPassword = EncryptionUtils.encrypt(plainPassword);

        try {
            Properties properties = new Properties();
            File configFile = new File("src/main/java/Resources/config.properties");
            
            if (configFile.exists()) {
                try (FileInputStream instream = new FileInputStream(configFile)) {
                    properties.load(instream);
                }
            }
            
            properties.setProperty("PASSWORD", encryptedPassword);
            
            try (FileOutputStream outstream = new FileOutputStream(configFile)) {
                properties.store(outstream, "Updated Encrypted Password");
                System.out.println("Password encrypted and saved successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decrypt(String encryptedText) {
        return EncryptionUtils.decrypt(encryptedText);
    }
}
