package com.erling.serviceJ.opencv.image;

import com.erling.nativeJ.opencvJ.implementation.Instance;
import com.erling.nativeJ.opencvJ.libinterface.basic.ImageCore;
import com.erling.nativeJ.opencvJ.libinterface.basic.image.EdgeDetection;
import com.erling.nativeJ.opencvJ.libinterface.utils.ClearUtils;
import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.sun.jna.Pointer;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


public class ImageService {
    private static final ImageCore imageJNA = Instance.IMAGE_JNA_D.getInstance();
    private static final ClearUtils clearUtils = Instance.CLEAR_UTILS_D.getInstance();
    private static final EdgeDetection edgeDetectionJNA = Instance.EDGE_DETECTION_JNA_D.getInstance();
    public static byte[] CannyEdgeDetection(MultipartFile file) {
        long startTime = System.currentTimeMillis();
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new IllegalArgumentException("仅支持图片格式上传");
        }
        Pointer CoreImage = null;
        Pointer edgePointer = null;
        ImageStruct  imageStruct = null;
        try {
            byte[] originalBytes = file.getBytes();
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("仅支持图片格式上传");
            }
            CoreImage =   imageJNA.createCoreImage();
            edgePointer = edgeDetectionJNA.CreateEdgeDetection(CoreImage);
            imageStruct = edgeDetectionJNA.TFastCanny(
                    edgePointer,
                    originalBytes,
                    originalBytes.length,
                    ".webp",
                    50,
                    150,
                    90
            );
            return  imageStruct.getBytes();
        }catch (Exception e){
            return null;
        }finally {
            clearUtils.ClearCoreImage(CoreImage);
            clearUtils.ClearEdgeDetector(edgePointer);
            clearUtils.ClearImageStruct(imageStruct);
            long endTime = System.currentTimeMillis();
            System.out.println("CannyEdgeDetection耗时：" + (endTime - startTime) + "ms");
        }

    }
    public static byte[] CannyEdgeDetectionFor_byt(byte[] file) {
        long startTime = System.currentTimeMillis();

        Pointer CoreImage = null;
        Pointer edgePointer = null;
        ImageStruct  imageStruct = null;
        try {
            byte[] originalBytes = file;

            CoreImage =   imageJNA.createCoreImage();
            edgePointer = edgeDetectionJNA.CreateEdgeDetection(CoreImage);
            imageStruct = edgeDetectionJNA.TFastCanny(
                    edgePointer,
                    originalBytes,
                    originalBytes.length,
                    ".webp",
                    50,
                    150,
                    90
            );
            return  imageStruct.getBytes();
        }catch (Exception e){
            return null;
        }finally {
            clearUtils.ClearCoreImage(CoreImage);
            clearUtils.ClearEdgeDetector(edgePointer);
            clearUtils.ClearImageStruct(imageStruct);
            long endTime = System.currentTimeMillis();
            System.out.println("CannyEdgeDetection耗时：" + (endTime - startTime) + "ms");
        }

    }

    public static byte[] LaplacianEdgeDetection(
            MultipartFile file,
            double scale, double delta, int imageQos)
    {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new IllegalArgumentException("仅支持图片格式上传");
        }
        Pointer CoreImage = null;
        ImageStruct  imageStruct = null;
        try {
            byte[] originalBytes = file.getBytes();

            CoreImage = imageJNA.createCoreImage();
            imageStruct = imageJNA.OpenImage(CoreImage, originalBytes, originalBytes.length);
            return imageJNA.
                    Laplacian(
                            CoreImage,
                            imageJNA.ImageforMat(CoreImage, imageStruct),
                            scale, delta, imageQos).
                    getBytes();

        }catch (Exception e){
            return null;
        }finally {
            imageJNA.ClearImage(CoreImage, imageStruct);
        }
    }

    public static byte[] FastCannyEdgeDetection(
            MultipartFile file
    ){
        long startTime = System.currentTimeMillis();
        Pointer edgePointer = null;
       try {
           edgePointer = edgeDetectionJNA.CreateEdgeDetection(imageJNA.createCoreImage());
           byte[] originalBytes = file.getBytes();
           return originalBytes;
       }catch (Exception e){
           return null;
       }finally {
           clearUtils.ClearEdgeDetector(edgePointer);
           long endTime = System.currentTimeMillis();
           System.out.println("FastCannyEdgeDetection耗时：" + (endTime - startTime) + "ms");
       }
    }
    public static byte[] FastCannyEdgeDetectionByte(
            byte[] file
    ){
        long startTime = System.currentTimeMillis();
        Pointer edgePointer = null;
        try {
            edgePointer = edgeDetectionJNA.CreateEdgeDetection(imageJNA.createCoreImage());
            byte[] originalBytes = edgeDetectionJNA.FastSobel(
                    edgePointer,
                    file,
                    file.length,
                    ".webp",
                    5,
                    0,
                    3,
                    1,
                    0,
                    90
            ).getBytes();
            return originalBytes;
        }catch (Exception e){
            return null;
        }finally {
            clearUtils.ClearEdgeDetector(edgePointer);
            long endTime = System.currentTimeMillis();
            System.out.println("FastCannyEdgeDetection耗时：" + (endTime - startTime) + "ms");
        }
    }
}
