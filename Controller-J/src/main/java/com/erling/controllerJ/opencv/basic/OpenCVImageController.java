package com.erling.controllerJ.opencv.basic;

import com.erling.serviceJ.opencv.image.ImageService;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/module/opencv/basic/image")
public class OpenCVImageController {


    @PostMapping(
            value = "/Canny",  // 确保路径正确
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // 确保请求体包含文件数据
    )
    public ResponseEntity<byte[]> Canny_(@RequestParam("file")  MultipartFile file) {
        return ResponseEntity.
                status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG).
                body(
                ImageService.CannyEdgeDetection(file)
                );
    }





    /**
     * 拉普拉斯算子边缘检测
     * @param file 图片文件
     * @param scale 缩放比例
     * @param delta 阈值
     * @param imageQos 图像质量
     * @return 处理后的图片
     */
    @PostMapping(
            value = "/Laplacian",  // 确保路径正确
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // 确保请求体包含文件数据
    )
    public ResponseEntity<byte[]> Laplacian(
            @RequestParam("file") MultipartFile file,
            @RequestParam("scale") double scale,
            @RequestParam("delta") double delta,
            @RequestParam("imageQos") int imageQos){
        return ResponseEntity.
                status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG).
                body(
                        ImageService.LaplacianEdgeDetection(file,scale,delta,imageQos)
                );
    }
    private byte[] testImage;

    // 添加consumes属性确保接收multipart
    @PostMapping(value = "/getEsp32camImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件为空");
        }
        this.testImage = file.getBytes();
        return ResponseEntity.ok("上传成功");
    }

    @GetMapping("/getTestImage")
    public ResponseEntity<byte[]> getTestImage() {
        if (this.testImage == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(ImageService.CannyEdgeDetectionFor_byt(this.testImage));
    }


}
