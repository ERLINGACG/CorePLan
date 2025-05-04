
import com.erling.utilJ.cryptography.CryptographyAES;
import com.erling.utilJ.cryptography.CryptographySHA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Arrays;

public class CryptographyTest {
    /**
     * 测试AES密钥生成
     */
    @Test
    public void generateAESKeyTest() throws Exception {        // 测试AES密钥生成
        SecretKey secretKey = CryptographyAES.generateAESKey();
        Assertions.assertNotNull(secretKey);//断言secretKey不为空
        System.out.println(Arrays.toString(secretKey.getEncoded()));
    }
    /**
     * 测试AES加密
     */
    @Test
    public void aesEncryptTest() throws Exception {            // 测试AES加密
        String plaintext = "Hello, World!";
        SecretKey secretKey = CryptographyAES.generateAESKey();
        String encrypted = CryptographyAES.aesEncrypt(plaintext, secretKey);
        System.out.println("密钥："+Arrays.toString(secretKey.getEncoded()));
        System.out.println("密文："+encrypted);
        Assertions.assertNotEquals(plaintext, encrypted);//断言加密后与原文不相同
    }


    /**
     * 测试AES解密
     */
    @Test
    public void aesDecryptTest() throws Exception {            // 测试AES解密
        String plaintext = "Hello, World!";
        SecretKey secretKey = CryptographyAES.generateAESKey();
        String encrypted = CryptographyAES.aesEncrypt(plaintext, secretKey);
        String decrypted = CryptographyAES.aesDecrypt(encrypted, secretKey);
        System.out.println("密钥："+Arrays.toString(secretKey.getEncoded()));
        System.out.println("密文："+encrypted);
        System.out.println("明文："+decrypted);
        Assertions.assertEquals(plaintext, decrypted);//断言解密后与原文相同
    }

    @Test
    public void  CryptographySHA256Test() throws Exception {
        String plaintext = "Hello, World!";
        String salt = "salt";
        String salt2=CryptographySHA.sha256(plaintext, salt);
        String hashed = CryptographySHA.sha256(plaintext, salt2);
        System.out.println("原始字符串："+plaintext);
        System.out.println("盐值："+salt2);
        System.out.println("哈希值："+hashed);
        Assertions.assertNotEquals(plaintext, hashed);//断言加密后与原文不相同

        //6d6416cd632155fcf1a3a977663d89bf1b5eadc3c7db7bdd5bf6f70d5329c8cc
    }
}
