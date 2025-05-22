#ifndef CLEAR_H
#define CLEAR_H
#include "webcore/data/ImageStruct.h"
#include "webcore/image/basic/WebCoreImage.h"
#include "webcore/image/basic/EdgeDetection.h"
namespace utilsclear{
    int clearImageStruct(ImageStruct* prt);
    int clearWebCoreImage(webcoreimage::CoreImage* prt);
    int clearMat(cv::Mat* prt);
    int clearEdgeDetector(edgedetection::EdgeDetection* prt);
};

#endif // CLEAR_H