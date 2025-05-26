package image;

import com.erling.nativeJ.opencvJ.implementation.Instance;
import com.erling.nativeJ.opencvJ.libinterface.basic.ImageCore;
import com.erling.nativeJ.opencvJ.libinterface.basic.image.EdgeDetection;
import com.erling.nativeJ.opencvJ.libinterface.utils.ClearUtils;
import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.erling.nativeJ.opencvJni.implementation.InstanceJNI;
import com.erling.nativeJ.opencvJni.libinterface.basic.EdgeDetectionJni;
import com.erling.nativeJ.opencvJni.libinterface.basic.image._EdgeDetectionJni;
import com.sun.jna.Pointer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JNITestLoad {
//    @Test
//    public void test(){
//        EdgeDetectionJni edgeDetectionJni = InstanceJNI.TEST_INSTANCE.getInstance();
//        Assertions.assertNotNull(edgeDetectionJni);
//        int result = edgeDetectionJni.ADD(1, 2);
//        Assertions.assertEquals(3,result);
//        System.out.println("result = " + result);
//    }
    @Test
    public void test1(){
        try (FileInputStream fis = new FileInputStream("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\image\\Sample1.jpg")) {
            // 先读取数据再调用 native 方法
            byte[] data = fis.readAllBytes(); // Java 9+ 语法

            EdgeDetectionJni edgeDetectionJni = InstanceJNI.TEST_INSTANCE.getInstance();
            Assertions.assertNotNull(edgeDetectionJni);
            long start = System.currentTimeMillis();
            byte[] result = edgeDetectionJni.TestCanny(data);
            long end = System.currentTimeMillis();
            System.out.println("解码耗时: " + (end - start) + " ms");
            Assertions.assertNotNull(result);
            Path outputPath = Paths.get("processed_canny_4.webp");
            Files.write(outputPath, result);
            Assertions.assertNotNull(result);
            System.out.println("保存成功，路径：" + outputPath.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Exception occurred:"+e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void NoCUDACannyTest(){
        Pointer edgePointer = null;
        Pointer imageCore  = null;
        Pointer mat = null;
        ImageStruct imageStruct_0 = null;
        ImageStruct imageStruct_1 = null;
        EdgeDetection edgeDetection = Instance.EDGE_DETECTION_JNA_D.getInstance();
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

            ;
            imageStruct_0 = edgeDetection.TFastCanny(
                    edgePointer,
                    data,
                    data.length,
                    ".webp",
                    50,
                    150,
                    90
            );

            // 正式测试
            long total = 0;
            int runs = 100;
            for(int i=0; i<runs; i++){
                long start_ = System.nanoTime();
                imageStruct_0 =  edgeDetection.TFastCanny(
                        edgePointer,
                        data,
                        data.length,
                        ".webp",
                        50,
                        150,
                        90
                );
                long end_ = System.nanoTime();
                if(i > 2) total += (end_ - start_); // 跳过前3次
            }
            System.out.println("平均耗时：" + (total/(runs-3))/1_000_000.0 + " ms");

        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    @Test
    public void FatNoCUDACannyTest(){
        try (FileInputStream fis = new FileInputStream("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\image\\Sample1.jpg")) {
            // 先读取数据再调用 native 方法
            byte[] data = fis.readAllBytes(); // Java 9+ 语法

            EdgeDetectionJni edgeDetectionJni = InstanceJNI.TEST_INSTANCE.getInstance();
            Assertions.assertNotNull(edgeDetectionJni);
            long start = System.currentTimeMillis();
            byte[] result = edgeDetectionJni.TestNoCudaCanny(data);
            long end = System.currentTimeMillis();
            System.out.println("初次解码耗时: " + (end - start) + " ms");

            long total = 0;
            int runs = 100;
            for(int i=0; i<runs; i++){
                long start_ = System.nanoTime();
                result = edgeDetectionJni.TestNoCudaCanny(data);
                long end_ = System.nanoTime();
                if(i > 2) total += (end_ - start_); // 跳过前3次
            }
            System.out.println("完毕 平均耗时：" + (total/(runs-3))/1_000_000.0 + " ms");
            Assertions.assertNotNull(result);
            Path outputPath = Paths.get("processed_canny_6.webp");
            Files.write(outputPath, result);
            Assertions.assertNotNull(result);
            System.out.println("保存成功，路径：" + outputPath.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Exception occurred:"+e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public  void NewSobelJNI(){
        try (FileInputStream fis = new FileInputStream("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\image\\Sample1.jpg")) {
            // 先读取数据再调用 native 方法
            byte[] data = fis.readAllBytes(); // Java 9+ 语法

            _EdgeDetectionJni edgeDetectionJni = InstanceJNI.EDGE_DETECTION_INSTANCE.getInstance();
            Assertions.assertNotNull(edgeDetectionJni);
            long start = System.currentTimeMillis();
            byte[] result = edgeDetectionJni.SobelJNI(
                    data,
                    ".webp",
                    3,
                    0,
                    1,
                    3,
                    0,
                    90
            );
            long end = System.currentTimeMillis();
            System.out.println("耗时: " + (end - start) + " ms");
            Assertions.assertNotNull(result);
            Path outputPath = Paths.get("processed_canny_7.webp");
            Files.write(outputPath, result);
            Assertions.assertNotNull(result);
            System.out.println("保存成功，路径：" + outputPath.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Exception occurred:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
