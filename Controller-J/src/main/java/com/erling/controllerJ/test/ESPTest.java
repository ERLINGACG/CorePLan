package com.erling.controllerJ.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ESPTest {
    @PostMapping("/test/esp")
    public String espTest() {
        System.out.println("ESP8266 尝试请求");
        return "esp test";
    }
}
