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