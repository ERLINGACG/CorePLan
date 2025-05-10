package com.erling.controllerJ.test;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@RestController
public class ErrorTest {
    @GetMapping("/test/error")
    public void testError(HttpServletResponse response) throws Exception {
        String path = "invalid_path";
        File file = new File(path, "Sample.jpg");

        // 不进行try-catch，让全局处理器捕获
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
