package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {
    private static final String AES_ALGORITHM = "AES";
    private static final byte[] SECRET_KEY = "1234567890123456".getBytes();

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY, AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY, AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
