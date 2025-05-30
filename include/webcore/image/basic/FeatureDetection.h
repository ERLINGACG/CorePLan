#ifndef _FEATUREDETECTION_H_
#define _FEATUREDETECTION_H_
#include "webcore/image/basic/WebCoreImage.h"
#include "webcore/data/ImageStruct.h"
#include "webcore/data/FeatureData.h"
// using namespace data;
namespace featuredetection{
    class FeatureDetector{
        private:
            webcoreimage::CoreImage* coreImage;
        public:
            FeatureDetector(webcoreimage::CoreImage* coreImage);
            ~FeatureDetector()=default;

            std::unique_ptr<ImageStruct> FastSURF( //绘制特征点
                unsigned char*  image,
                int             size,
                const char*     imagecode,
                int             imageQos =90
            );
            
            /// @brief 
            /// SURF特征点匹配
            /// @param image 待匹配图像
            /// @param size 待匹配图像大小
            /// @param templateImage 参考图像
            /// @param templateSize 参考图像大小
            /// @param imagecode 图像编码
            /// @param imageQos 图像质量
            /// @param featureMatch 特征点匹配结果
            /// @return std::unique_ptr<ImageStruct>  匹配结果图像
            std::unique_ptr<ImageStruct> CompareSURF( //比较特征点
                unsigned char*  image,
                int             size,
                unsigned char*  templateImage,
                int             templateSize,
                const char*     imagecode,
                int             imageQos, 
                FeatureMatch&   featureMatch
            );
            /**
             * @brief 
             * ORB特征点匹配
             * @param image 待匹配图像
             * @param size 待匹配图像大小
             * @param templateImage 参考图像
             * @param templateSize 参考图像大小
             * @param imagecode 图像编码
             * @param imageQos 图像质量
             * @param featureMatch  特征点匹配结果
             * @return std::unique_ptr<ImageStruct> 
             */
            std::unique_ptr<ImageStruct> CompareORB( //比较特征点
                unsigned char*  image,
                int             size,
                unsigned char*  templateImage,
                int             templateSize,
                const char*     imagecode,
                int             imageQos, 
                FeatureMatch&   featureMatch
            );

            
    };

}
#endif // _FEATUREDETECTION_H_