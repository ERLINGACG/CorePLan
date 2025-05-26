package com.erling.serviceJ.opencv.image;

import com.erling.nativeJ.opencvJ.implementation.Instance;
import com.erling.nativeJ.opencvJ.libinterface.basic.ImageCore;
import com.erling.nativeJ.opencvJ.libinterface.basic.image.EdgeDetection;
import com.erling.nativeJ.opencvJ.libinterface.utils.ClearUtils;
import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.sun.jna.Pointer;

public class EdgeDetectionService {
    private static final ImageCore imageJNA = Instance.IMAGE_JNA_D.getInstance();
    private static final ClearUtils clearUtils = Instance.CLEAR_UTILS_D.getInstance();
    private static final EdgeDetection edgeDetectionJNA = Instance.EDGE_DETECTION_JNA_D.getInstance();

    public static byte[] FastLaplacian(byte[] image) {
        Pointer coreImage = imageJNA.createCoreImage();
        Pointer edgePointer = edgeDetectionJNA.CreateEdgeDetection(coreImage);
        ImageStruct imageStruct = null;
        try{
            long startTime = System.currentTimeMillis();
            imageStruct =edgeDetectionJNA.FastLaplacian(
                    edgePointer,
                    image,
                    image.length,
                    ".jpeg",
                    1.0,
                    0.0,
                    90
            );
            long endTime = System.currentTimeMillis();
            System.out.println("拉普拉斯边缘检测耗时: " + (endTime - startTime) + " ms");
            return imageStruct.getBytes();
        }catch (Exception e){
            return null;
        }finally {
           clearUtils.ClearImageStruct(imageStruct);
           clearUtils.ClearCoreImage(coreImage);
           clearUtils.ClearEdgeDetector(edgePointer);
        }
    }
    public static byte[] FastSobel(byte[] image) {
        Pointer coreImage = imageJNA.createCoreImage();
        Pointer edgePointer = edgeDetectionJNA.CreateEdgeDetection(coreImage);
        ImageStruct imageStruct = null;
        try{
            long startTime = System.currentTimeMillis();
            imageStruct =edgeDetectionJNA.FastSobel(
                    edgePointer,
                    image,
                    image.length,
                    ".jpeg",
                    3,
                    0,
                    3,
                    1,
                    0,
                    90
            );
            long endTime = System.currentTimeMillis();
            System.out.println("Sobel边缘检测耗时: " + (endTime - startTime) + " ms");
            return imageStruct.getBytes();
        }catch (Exception e){
            return null;
        }finally {
            clearUtils.ClearImageStruct(imageStruct);
            clearUtils.ClearCoreImage(coreImage);
            clearUtils.ClearEdgeDetector(edgePointer);
        }
    }
}
