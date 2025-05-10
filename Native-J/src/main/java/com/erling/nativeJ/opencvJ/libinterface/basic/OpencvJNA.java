package com.erling.nativeJ.opencvJ.libinterface.basic;

import com.erling.nativeJ.opencvJ.config.OpenCVConfig;
import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * OpenCV的JNA接口
 * @author erling
 * @version 1.0
 * @since 2025/5/12/13:10

 */
public interface OpencvJNA extends Library {

    Pointer createCoreImage();
    ImageStruct OpenImage(Pointer coreImage, byte[] data, int size);
    int  ClearImage(Pointer coreImage, ImageStruct imageStruct);
    Pointer ImageforMat(Pointer coreImage,ImageStruct imageStruct);
    ImageStruct MatforImage(Pointer coreImage, Pointer mat);

    ImageStruct Canny(Pointer coreImage,
                      Pointer mat,
                      double threshold1,
                      double threshold2,
                      int imageQos);
    ImageStruct Laplacian(Pointer coreImage,
                          Pointer mat,
                          double scale,        // 缩放比例
                          double delta,         // 阈值
                          int imageQos);
}
