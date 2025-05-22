#include "webcore/WebCore.h"
#include "webcore/utils/Clear.h"
using namespace webcoreimage;
using namespace edgedetection;
extern "C"{
    CORE_API int ClearMat(cv::Mat* mat){
        return utilsclear::clearMat(mat);
    }
    CORE_API int ClearImageStruct(ImageStruct* imageStruct){
        return utilsclear::clearImageStruct(imageStruct);
    }
    CORE_API int ClearCoreImage(CoreImage* coreImage){
        return utilsclear::clearWebCoreImage(coreImage);
    }
    CORE_API int ClearEdgeDetector(EdgeDetection* edgeDetector){
        return utilsclear::clearEdgeDetector(edgeDetector);
    }
}