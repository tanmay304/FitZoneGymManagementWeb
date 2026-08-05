package com.fitzone.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility for password hashing, MD5 verification, BCrypt generation, and auto-upgrading.
 */
public class PasswordUtil {

    public static String hashPasswordBCrypt(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(12));
    }

    public static boolean checkPasswordBCrypt(String plainText, String hashed) {
        if (hashed == null || !hashed.startsWith("$2a$") && !hashed.startsWith("$2b$") && !hashed.startsWith("$2y$")) {
            return false;
        }
        return BCrypt.checkpw(plainText, hashed);
    }

    public static String hashPasswordMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(plainText.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 digest algorithm not available", e);
        }
    }

    public static boolean checkPasswordMD5(String plainText, String md5Hash) {
        if (md5Hash == null || md5Hash.length() != 32) {
            return false;
        }
        return hashPasswordMD5(plainText).equalsIgnoreCase(md5Hash);
    }

    public static boolean isMd5Hash(String hash) {
        return hash != null && hash.length() == 32 && hash.matches("^[a-fA-F0-9]{32}$");
    }
}
