package com.erling.testJ;

import com.erling.serviceJ.opencv.image.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenCVTest {
    @Test
    public void testOpenCV() throws IOException {
        File file = new File("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\image\\Sample1.jpg");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);

        // 将byte数组包装成MultipartFile
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                file.getName(),
                "image/jpeg",
                data
        );

        byte[] processedBytes = ImageService.CannyEdgeDetection(multipartFile);
        Path outputPath = Paths.get("processed_image2.jpg");
        Files.write(outputPath, processedBytes);
    }
}
