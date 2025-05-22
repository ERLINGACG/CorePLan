#include "webcore/WebCore.h"
using namespace webcoreimage;
using namespace edgedetection;
extern "C"{
    CORE_API EdgeDetection* CreateEdgeDetection(CoreImage* coreImage){
        return new EdgeDetection(coreImage);
    }
}



 extern "C"{
    /**
     * @brief 
     * 边缘检测
     * @param coreImage  CoreImage对象
     * @param image      输入图像
     * @param threshold1 低阈值
     * @param threshold2 高阈值
     * @param imageqos   图像质量
     * @return ImageStruct* 输出图像
     */
    CORE_API ImageStruct* TCanny(    //边缘检测
            EdgeDetection* edgeDetection, // EdgeDetection对象
            cv::Mat* image,         // 输入图像
            double threshold1,      // 低阈值
            double threshold2,      // 高阈值 
            int  imageqos         // 图像质量
    ){
        try{
          return edgeDetection->Canny(
              image,
              threshold1,
              threshold2,
              imageqos).release();
        }catch(...){
            std::cout<<"error"<<std::endl;
            return nullptr;
        }
    }
    /// @brief 
    /// 快速边缘检测
    /// @param edgeDetection  EdgeDetection对象
    /// @param image          输入图像byte[]
    /// @param size           输入图像byte[]大小
    /// @param imagecode      图像编码
    /// @param threshold1     低阈值     
    /// @param threshold2     高阈值     
    /// @param imageqos       图像质量
    /// @return ImageStruct*  输出图像结构体  
    CORE_API ImageStruct* TFastCanny(    //边缘检测
            EdgeDetection* edgeDetection, // EdgeDetection对象
            unsigned char* image,         // 输入图像
            int size,               // 输入图像大小
            const char* imagecode,
            double      threshold1, 
            double      threshold2,
            int         imageqos
    ){
        try{
            return edgeDetection->InlineCanny(
                image,
                size,
                imagecode,
                threshold1,
                threshold2,
                imageqos
            ).release();
        }catch(...){
            return nullptr;
        }
    }

    /**
     * @brief 
     * 拉普拉斯算子
     * @param coreImage  CoreImage对象
     * @param image      输入图像
     * @param scale      缩放比例
     * @param delta      阈值
     * @param imageqos   图像质量
     * @return ImageStruct* 输出图像
     */
    CORE_API ImageStruct* Laplacian(CoreImage* coreImage,
            cv::Mat* image,
            double scale,      
            double delta,        
            int imageqos
    ){                          
       try{
          return coreImage->Laplacian(
            image,
            scale,
            delta,
            imageqos).release();
       }catch(...){
            std::cout<<"error"<<std::endl;
           return nullptr;
       }
    }
    CORE_API ImageStruct* FastLaplacian(
        EdgeDetection* edgeDetection,
        unsigned char*  image,
        int             size,
        const char*     imagecode,
        double          scale,       // 缩放比例
        double          delta,       // 阈值
        int             imageqos
    ){
        try{
            return edgeDetection->InlineLaplacian(
                image,
                size,
                imagecode,
                scale,
                delta,
                imageqos).release();
        }catch(...){
            std::cout<<"error"<<std::endl;
            return nullptr;

        }
    }

    CORE_API void Debug(){
   
    }
}