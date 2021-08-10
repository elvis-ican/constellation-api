package dev.wre.api.util;

import org.junit.jupiter.api.Assertions;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

public class TokenUtil {

//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecretKey encodedKey = TokenUtil.getKeyFromPassword("aVerySecurePassword");
//        String encodedString = TokenUtil.convertSecretKeyToString(encodedKey);
//        SecretKey decodedKey = TokenUtil.convertStringToSecretKey(encodedString);
//        SecretKey encodedKey2 = TokenUtil.getKeyFromPassword("notSoSecurePassword");
//        String encodedString2 = TokenUtil.convertSecretKeyToString(encodedKey2);
//        SecretKey decodedKey2 = TokenUtil.convertStringToSecretKey(encodedString2);
//        Assertions.assertEquals(encodedKey, decodedKey);
//        Assertions.assertFalse(decodedKey.equals(decodedKey2));
//        System.out.println(decodedKey.equals(decodedKey2));
//        System.out.println(generateRandomString(16));
//        System.out.println(isValidToken("aVerySecurePassword", encodedString));
//    }

    public static boolean isValidToken(String username, String token) {
        SecretKey encodedKey = null;
        String originalToken = null;
        try {
            encodedKey = TokenUtil.getKeyFromPassword(username);
            originalToken = TokenUtil.convertSecretKeyToString(encodedKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e ) {
            e.printStackTrace();
        }
        return originalToken.equals(token);
    }

    /**
     * Generate a secret key from a password
     * @param password
     * @return an encoded key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getKeyFromPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = ">U&5!;f)Y1)n{9so@";
        //String salt = System.getenv("SALT");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey originalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return originalKey;
    }

    /**
     * Convert secret key to a string
     * @param secretKey
     * @return encoded key string
     * @throws NoSuchAlgorithmException
     */
    public static String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    /**
     * Convert encoded key string back to originalKey
     * @param encodedKey
     * @return original key
     */
    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    /**
     * Generate a random string with characters from '!' to '}'
     * @param strLength
     * @return
     */
    public static String generateRandomString(int strLength) {
        Random random = new Random();
        String generatedString = random.ints(33, 126)
                .filter(i -> (i != 34 && i != 39 && i != 47 && i != 92)) // get rid of ", ', \, /
                .limit(strLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
