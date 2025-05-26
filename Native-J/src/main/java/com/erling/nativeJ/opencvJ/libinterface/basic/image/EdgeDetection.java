package com.erling.nativeJ.opencvJ.libinterface.basic.image;

import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface EdgeDetection extends Library {

    Pointer  CreateEdgeDetection(Pointer coreImage);
    ImageStruct TCanny(Pointer edgeDetection,
                      Pointer mat,
                      double threshold1,
                      double threshold2,
                      int imageQos);

    ImageStruct TFastCanny(
            Pointer edgeDetection,
            byte[] imageData,
            int size,
            String imageCode,
            double threshold1,
            double threshold2,
            int    imageQos
    );

    ImageStruct FastLaplacian(
            Pointer         edgeDetection,
            byte[]          imageData,
            int             size,
            String          imageCode,
            double          scale,       // 缩放比例
            double          delta,       // 阈值
            int             imageQos
    );
    ImageStruct FastSobel(
            Pointer         edgeDetection,
            byte[]          imageData,
            int             size,
            String          imageCode,
            int             gauss_size ,      // 高斯滤波器大小
            double          sigmaX     ,          // x方向标准差
            int             ksize      ,
            double          scale      ,   // 缩放因子
            double          delta      ,   // 增量值
            int             imageQos
    );

    void Debug();
    ImageStruct Laplacian(Pointer coreImage,
                          Pointer mat,
                          double scale,        // 缩放比例
                          double delta,         // 阈值
                          int imageQos);
}
