package com.erling.utilJ.cryptography;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptographySHA {
    /**
     * @param input 明文
     * @param salt 盐
     * @return 密文
     */
    public static String sha256(String input, String salt) throws NoSuchAlgorithmException {
        String combined = input + salt;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(combined.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(hashBytes);
    }
    /**
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    /**
     * @param length 盐的长度
     * @return 随机生成的盐
     */
    public static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }
}
