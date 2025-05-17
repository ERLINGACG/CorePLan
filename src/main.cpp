#include "webcore/WebCore.h"
#include "webcore/utils/Clear.h"
using namespace webcoreimage;

 extern "C"  {
    CORE_API CoreImage* createCoreImage(void) {
        return new CoreImage();
    }
    CORE_API ImageStruct* OpenImage(CoreImage* coreImage,unsigned char* data,int size){
        return coreImage->openImage(data,size).release(); //返回原始指针类型
    }
    CORE_API int ClearImage(
        CoreImage* coreImage,
        ImageStruct* imageStruct
       
        ){
        try{
            utilsclear::clearImageStruct(imageStruct);
            utilsclear::clearWebCoreImage(coreImage);
            return 0;
        }catch(...){
            return -1;
        }
    }
    CORE_API cv::Mat* ImageforMat(CoreImage* coreImage,ImageStruct* imageStruct){
       return coreImage->ImageforMat(imageStruct).release();
    }

    CORE_API ImageStruct* MatforImage(CoreImage* coreImage,cv::Mat* mat){
        return coreImage->MatforImage(mat).release();
    }



}

 extern "C"{
    CORE_API ImageStruct* Canny(    //边缘检测
        CoreImage* coreImage,
            cv::Mat* image,         // 输入图像
            double threshold1,      // 低阈值
            double threshold2,      // 高阈值 
            int  imageqos         // 图像质量
    ){
        try{
          return coreImage->Canny(
              image,
              threshold1,
              threshold2,
              imageqos).release();
        }catch(...){
            std::cout<<"error"<<std::endl;
            return nullptr;
        }
    }
    CORE_API ImageStruct* Laplacian(CoreImage* coreImage,
            cv::Mat* image,
            double scale,        // 缩放比例
            double delta,         // 阈值
            int imageqos
    ){                          //拉普拉斯算子
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
 }