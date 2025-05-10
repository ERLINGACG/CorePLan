package com.erling.utilJ.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_MS = 3600000; // 1小时

    /**
     * 生成JWT令牌

     * @return 生成的JWT令牌
     */
    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }
    /**
     * 验证JWT令牌
     * @param token JWT令牌
     * @return 用户名
     */
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    /**
     * 验证JWT令牌
     * @param token JWT令牌
     * @return 令牌过期时间
     */
    public static String extractTime(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt().toString();
    }
    /**
     * 验证JWT令牌
     * @param token JWT令牌
     * @param _username 用户名
     * @return 是否有效
     */
    public static boolean validateToken(String token, String _username) {
        final String username = extractUsername(token);
        return (username.equals(_username) && !isTokenExpired(token));
    }

    public static boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
    /**
     * 生成包含JWT的HTTP Cookie
     * @param username 用户名
     * @return 包含JWT的HTTP Cookie
     */
    public static Cookie generateJwtCookie(String username) {
        // ... 原有代码不变 ...

        String jwt = generateToken(username);
        // 使用ResponseCookie构建更标准的Cookie
        ResponseCookie responseCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(true)
                .secure(false) // 本地开发禁用secure
                .path("/")
                .maxAge((int) (EXPIRATION_MS / 1000))
                .sameSite("None")  // 明确设置SameSite
                .domain("localhost")
                .build();

        Cookie cookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
        cookie.setPath(responseCookie.getPath());
        cookie.setMaxAge((int) responseCookie.getMaxAge().getSeconds());
        cookie.setSecure(responseCookie.isSecure());
        return cookie;
    }


}
