package com.erling.controllerJ.interceptor;

import com.erling.utilJ.jwt.JwtUtils;
import com.erling.utilJ.result.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterTest {

    @GetMapping("/test/interceptor")
    public ResponseEntity<Result<?>> TestF() {
        return ResponseEntity.ok(Result.SUCCESS("interceptor test"));
    }
    @GetMapping("/test/interceptor2")
    public String TestF2() {
        return JwtUtils.generateToken("test");
    }
}
