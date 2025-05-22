#ifndef WEBCOREIMAGE_H
#define WEBCOREIMAGE_H
#include <iostream>
#include  "opencv2/opencv.hpp"
#include <vector>
#include "opencv2/xfeatures2d.hpp"
#include "webcore/data/ImageStruct.h"
#include <memory> 



namespace webcoreimage {
    class CoreImage{
        public:
        /**
         * @brief 
         * 打开并解码图片
         * @param data  图片byte数组
         * @param size  图片byte数组长度
         * @return std::unique_ptr<ImageStruct> 图片结构体
         */
        std::unique_ptr<ImageStruct> openImage(unsigned char* data,int size);
        
        /** 
         * @brief 
         * 图片转Mat
         * @param image  图片结构体
         * @return std::unique_ptr<cv::Mat> 图片Mat
         */
        std::unique_ptr<cv::Mat>     ImageforMat(ImageStruct* image); 
        /** 
         * @brief 
         * Mat转图片
         * @param image  图片Mat
         * @return std::unique_ptr<ImageStruct> 图片结构体
         */
        std::unique_ptr<ImageStruct> MatforImage(cv::Mat* image);


        std::unique_ptr<ImageStruct> Canny(
            cv::Mat* image,             // 输入图像
            double threshold1,          // 低阈值
            double threshold2,         // 高阈值
            int  imageqos          // 图像质量
        );
        std::unique_ptr<ImageStruct> Sobel(
            cv::Mat* image
        );
        std::unique_ptr<ImageStruct> Laplacian(
            cv::Mat* image,
            double scale,       // 缩放比例
            double delta,       // 阈值
            int imageqos
        );
        std::unique_ptr<ImageStruct> Scharr(cv::Mat* image); 
           
           // 自动生成的析构函数
           ~CoreImage() = default;

    };

};
#endif // WEBCOREIMAGE_H