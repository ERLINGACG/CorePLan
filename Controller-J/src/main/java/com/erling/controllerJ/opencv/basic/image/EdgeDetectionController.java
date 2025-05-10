package com.erling.controllerJ.opencv.basic.image;

import com.erling.serviceJ.opencv.image.EdgeDetectionService;
import com.erling.serviceJ.opencv.image.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("/module/opencv/basic/image")
public class EdgeDetectionController {

    @PostMapping(
            value = "/FastLaplace",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // 确保请求体包含文件数据
    )
    public ResponseEntity<byte[]> FastLaplace(@RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.
                status(HttpStatus.OK).
                contentType(MediaType.IMAGE_JPEG).
                body(
                        EdgeDetectionService.FastLaplacian(file.getBytes())
                );
    }
}
