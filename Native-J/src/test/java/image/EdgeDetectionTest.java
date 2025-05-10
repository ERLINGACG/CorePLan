package image;

import com.erling.nativeJ.opencvJ.implementation.Instance;
import com.erling.nativeJ.opencvJ.libinterface.basic.ImageCore;
import com.erling.nativeJ.opencvJ.libinterface.basic.image.EdgeDetection;
import com.erling.nativeJ.opencvJ.libinterface.utils.ClearUtils;
import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EdgeDetectionTest {
    @Test
    public void CannyTest(){
        Pointer edgePointer = null;
        Pointer imageCore  = null;
        Pointer mat = null;
        ImageStruct imageStruct_0 = null;
        ImageStruct imageStruct_1 = null;
        EdgeDetection  edgeDetection = Instance.EDGE_DETECTION_JNA_D.getInstance();
        ImageCore imageJNA = Instance.IMAGE_JNA_D.getInstance();
        ClearUtils clearUtils = Instance.CLEAR_UTILS_D.getInstance();
        try {
            long startTime = System.currentTimeMillis();
            imageCore = imageJNA.createCoreImage();
            edgePointer =edgeDetection.CreateEdgeDetection(imageCore);
            File file = new File("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\image\\Sample1.jpg");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data); // 确保实际读取字节
//           imageStruct_0 = imageJNA.OpenImage(imageCore,data,data.length);
//           mat = imageJNA.ImageforMat(imageCore,imageStruct_0);
//           imageStruct_1 =edgeDetection.TCanny(
//                   edgePointer,
//                   mat,
//                   50,150,95
//           )
           ;
            imageStruct_0 = edgeDetection.TFastCanny(
                    edgePointer,
                    data,
                    data.length,
                    ".jpeg",
                    50,
                    150,
                    70

            );
            byte[] processedBytes = imageStruct_0.getBytes();

            Path outputPath = Paths.get("processed_canny_1.webp");
            Files.write(outputPath, processedBytes);
            System.out.println("处理时间：" + (System.currentTimeMillis() - startTime) + "ms");
            Assertions.assertNotNull(processedBytes);
            System.out.println("保存成功，路径：" + outputPath.toAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    @Test
    public void DebugTest(){
        EdgeDetection  edgeDetection = Instance.EDGE_DETECTION_JNA_D.getInstance();
        edgeDetection.Debug();
    }
}
