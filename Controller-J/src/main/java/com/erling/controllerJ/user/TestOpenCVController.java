package com.erling.controllerJ.user;

import com.erling.nativeJ.opencvJ.TWebCoreOpenCV;
import com.erling.nativeJ.opencvJ.implementation.Instance;
import com.erling.nativeJ.opencvJ.libinterface.basic.OpencvJNA;
import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.erling.utilJ.path.GetPath;
import com.sun.jna.Pointer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
@Tag(name = "Tutorial", description = "Tutorial management APIs")
@RestController
public class TestOpenCVController {

    OpencvJNA  opencvJNA= Instance.OPENCV_JNA_D.getInstance();

    @GetMapping("/testOpenCV1")
    public List<String> testOpenCV1() {
        return GetPath.getPath();
    }
    @GetMapping("/testOpenCV2")
    public ResponseEntity<byte[]> testOpenCV2() {
        byte[] imageData = TWebCoreOpenCV.openImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
    @GetMapping("/testOpenCV3")
    public ResponseEntity<byte[]> testOpenCV3() throws Exception {
        File file = new File("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\image\\Sample1.jpg");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data); // 确保实际读取字节
        Pointer pointer = opencvJNA.createCoreImage();
        ImageStruct imageStruct = opencvJNA.OpenImage(pointer, data, data.length);
        Pointer mat=opencvJNA.ImageforMat(pointer, imageStruct);
        long time = System.currentTimeMillis();
        byte[] imageData = opencvJNA.Laplacian(pointer,
                opencvJNA.ImageforMat(pointer, opencvJNA.Canny(pointer, mat, 50, 150,95))
                , 2, 150,95).getBytes();
        long endTime1 = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime1 - time) + "ms");
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
                    .body(TWebCoreOpenCV.SURFJ(imageBytes));
        } catch (Exception e) {
            return ResponseEntity.status(200).body(null);
        }
    }
}
