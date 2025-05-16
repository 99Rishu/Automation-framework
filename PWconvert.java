package resources;

import utils.EncryptionUtils;
import java.util.Scanner;

public class PWconvert {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for plain password
        System.out.print("Enter password to encrypt: ");
        String password = scanner.nextLine();

        // Encrypt password
        String encryptedPassword = EncryptionUtils.encrypt(password);

        // Display encrypted password
        System.out.println("Encrypted Password: " + encryptedPassword);
    }
}
