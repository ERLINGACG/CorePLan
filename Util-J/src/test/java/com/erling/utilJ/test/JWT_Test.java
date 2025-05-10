package com.erling.utilJ.test;

import com.erling.utilJ.jwt.JwtParamConfig;
import com.erling.utilJ.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public class JWT_Test {
    @Test
    public void generateToken() {

        String token = JwtUtils.generateToken("username");
        System.out.println(token);
    }
    @Test
    public void parseToken() {
        String token = JwtUtils.generateToken("username");
        String username = JwtUtils.extractUsername(token);
        System.out.println(username);
        Assertions.assertEquals("username", username);
    }
    @Test
    public void validateToken() {
        String token = JwtUtils.generateToken("username");
        boolean valid = JwtUtils.validateToken(token, "username");
        System.out.println(valid);
        Assertions.assertTrue(valid);
    }
    @Test
    public void ConfirmToken() {

        Key key=JwtParamConfig.SECRET_KEY.getSecretKey();
        Assertions.assertNotNull(key);
        System.out.println("key:"+key);

        Long expirationMs=JwtParamConfig.EXPIRATION_MS.getExpirationMs();
        Assertions.assertNotNull(expirationMs);
        System.out.println("expirationMs:"+expirationMs);

        Assertions.assertThrows(IllegalStateException.class,
                JwtParamConfig.EXPIRATION_MS::getSecretKey);
    }
}
