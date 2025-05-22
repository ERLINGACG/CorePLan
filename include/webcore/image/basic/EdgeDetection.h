#ifndef EDGEDETECTION_H
#define EDGEDETECTION_H
#include "webcore/image/basic/WebCoreImage.h"
#include "webcore/data/ImageStruct.h"
namespace edgedetection{
   class EdgeDetection{
    private:
       webcoreimage::CoreImage* coreImage;
    public:

        EdgeDetection(webcoreimage::CoreImage* coreImage);
        ~EdgeDetection()=default;


        /**
         * @brief 
         * 边缘检测
         * @param image 输入图像
         * @param threshold1 低阈值
         * @param threshold2 高阈值
         * @param imageqos 图像质量
         * @return std::unique_ptr<ImageStruct> 输出图像
         */
        std::unique_ptr<ImageStruct> Canny(
            cv::Mat* image,             // 输入图像
            double threshold1,          // 低阈值
            double threshold2,         // 高阈值
            int  imageqos          // 图像质量
        );

        /**
         * @brief 
         * 快速边缘检测
         * @param image  输入图像byte[]
         * @param size     图像大小
         * @param imagecode 图像编码
         * @param threshold1 低阈值
         * @param threshold2 高阈值
         * @param imageqos 图像质量
         * @return std::unique_ptr<ImageStruct> 输出图像
         */
        std::unique_ptr<ImageStruct> InlineCanny(
            unsigned char*  image,
            int             size,
            const char* imagecode,
            double      threshold1, double threshold2,
            int         imageqos
        );
        /**
         * @brief 
         * 快速拉普拉斯边缘检测
         * @param image  输入图像byte[]
         * @param size     图像大小
         * @param imagecode 图像编码
         * @param scale     缩放因子
         * @param delta     阈值
         * @param imageqos  图像质量
         * @return std::unique_ptr<ImageStruct>  输出图像
         */
       std::unique_ptr<ImageStruct> InlineLaplacian(
            unsigned char*  image,
            int             size,
            const char*     imagecode,
            double          scale,       // 缩放比例
            double          delta,       // 阈值
            int             imageqos
        );


        /** 
         * @brief 
         * 边缘检测
         * @param image 输入图像
         * @param scale 缩放因子
         * @param delta 阈值
         * @param imageqos 图像质量
         * @return std::unique_ptr<ImageStruct> 输出图像
         */
        std::unique_ptr<ImageStruct> Laplacian(
            cv::Mat* image,
            double scale,       // 缩放比例
            double delta,       // 阈值
            int imageqos
        );
        /** 
         * @brief 
         * Sobel 边缘检测
         * @param image 输入图像
         * @param gauss_size 高斯滤波器大小
         * @param sigmaX x方向标准差
         * @param ksize 卷积核大小
         * @param scale 缩放因子
         * @param delta 增量值
         * @param imageqos 图像质量
         * @return std::unique_ptr<ImageStruct> 输出图像        
         */
        std::unique_ptr<ImageStruct> Sobel(
                cv::Mat* image,
                int      gauss_size = 5,      // 高斯滤波器大小
                double   sigmaX     = 0,          // x方向标准差
                int      ksize      = 3, 
                double   scale      = 1,   // 缩放因子 (未显式指定)
                double   delta      = 0,   // 增量值 (未显式指定)
                int      imageqos   = 95
        );
        
   };
};

#endif // EDGEDETECTION_H