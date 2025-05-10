package com.erling.controllerJ.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DownLoad {
    @GetMapping("/download")
    public String download(
            HttpServletRequest request,
            HttpServletResponse response) {
        String path = "TEST";
        File file = new File(path, "Sample.jpg");
        try {
            // 验证文件存在性
            if (!file.exists()) {
                response.setStatus(404);
                return "File not found";
            }

            // 设置正确的响应头
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"Sample.jpg\"");

            // 写入响应流
            Files.copy(file.toPath(), response.getOutputStream());
            response.flushBuffer();

        } catch (IOException e) {
            response.setStatus(500);
            return "Download failed";
        }

        return null; // 因为内容已直接写入响应流
    }
}
