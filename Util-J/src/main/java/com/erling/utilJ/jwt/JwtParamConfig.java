package com.erling.utilJ.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Key;



public enum JwtParamConfig {
    SECRET_KEY(Keys.secretKeyFor(SignatureAlgorithm.HS256)),
    EXPIRATION_MS(3600000L);



    private final Key secretKey;
    private final Long expirationMs;

    JwtParamConfig(Key key) {
        this.secretKey = key;
        this.expirationMs=null ;
    }
    JwtParamConfig(Long EXPIRATION_MS) {
        this.secretKey = null;
        this.expirationMs = EXPIRATION_MS;
    }

    public Long getExpirationMs() {
        if (expirationMs == null) {
            throw new IllegalStateException("Expiration time is not set");
        }
        return expirationMs;
    }

    public Key getSecretKey() {
        if (secretKey == null) {
            throw new IllegalStateException("Secret key is not set");
        }
        return secretKey;
    }
}
