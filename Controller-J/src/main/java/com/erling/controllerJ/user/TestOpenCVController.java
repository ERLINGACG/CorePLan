package com.erling.controllerJ.user;

import com.erling.nativeJ.opencvJ.WebCoreOpenCV;
import com.erling.utilJ.path.GetPath;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class TestOpenCVController {
    @GetMapping("/testOpenCV1")
    public List<String> testOpenCV1() {
        return GetPath.getPath();
    }
    @GetMapping("/testOpenCV2")
    public ResponseEntity<byte[]> testOpenCV2() {
        byte[] imageData = WebCoreOpenCV.openImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    @PostMapping("/testuploadImage")
    public ResponseEntity<byte[]> testuploadImage(@RequestParam MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            System.out.println(imageBytes.length); // 输出文件大小
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(WebCoreOpenCV.SURFJ(imageBytes));
        } catch (Exception e) {
            return ResponseEntity.status(200).body(null);
        }
    }
}
