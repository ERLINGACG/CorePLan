package com.erling.controllerJ.test;

import com.erling.utilJ.jwt.JwtUtils;
import com.erling.utilJ.result.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieTest {
    @GetMapping("/test/cookie")
    public ResponseEntity<Result<?>> testCookie(@RequestParam String username,
                                                HttpServletResponse response) {
        // 生成标准HTTP Cookie
        response.addCookie(JwtUtils.generateJwtCookie(username));
        return ResponseEntity.ok()
                .body(Result.SUCCESS("Cookie set successfully."));
    }
    @GetMapping("/test/cookie/parse")
    public ResponseEntity<Result<?>> testCookieParse(@CookieValue(name = "JWT") String token, // 从Cookie获取JWT
                                                     @RequestParam String username) {
        System.out.println("JWT Token: " + token);
        return ResponseEntity.ok(Result.SUCCESS(JwtUtils.validateToken(token, username)));
    }
    @GetMapping("/test/cookie/parse2")
    public ResponseEntity<Result<?>> testCookieParse2(@CookieValue(name = "JWT") String token // 从Cookie获取JWT
                                                   ) {
        System.out.println("JWT Token: " + token);
        return ResponseEntity.ok(Result.SUCCESS(JwtUtils.isTokenExpired(token)));
    }
}
