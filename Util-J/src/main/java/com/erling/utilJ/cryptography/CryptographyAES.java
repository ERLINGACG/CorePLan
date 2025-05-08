package com.erling.utilJ.cryptography;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Schema(description = "AES",title = "AES加密解密")
public class CryptographyAES {

    /**
     * AES加密
     * @param plaintext 明文
     * @param secretKey 密钥
     * @return 密文
     * @throws Exception 加密异常
     */
    public static String aesEncrypt(String plaintext, SecretKey secretKey) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");         // 选择AES加密模式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16])); // 初始化加密器
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));  // 加密
        return Base64.getEncoder().encodeToString(encrypted);                          // 编码为Base64字符串
    }

    /**
     * AES密钥生成
     * @return 密钥
     */
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");  // 选择AES算法
        keyGen.init(256);                                        // 设置密钥长度为256位
        return keyGen.generateKey();                                     // 生成密钥
    }

    /**
     * AES解密
     * @param ciphertext 密文
     * @param secretKey 密钥
     * @return 明文
     * @throws Exception 解密异常
     */
    public static String aesDecrypt(String ciphertext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");         // 选择AES加密模式/填充方式
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16])); // 初始化解密器
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ciphertext));      // 解密
        return new String(decrypted, StandardCharsets.UTF_8);                           // 解码为UTF-8字符串
    }
}
