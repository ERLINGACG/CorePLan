#ifndef CLEAR_H
#define CLEAR_H
#include "webcore/data/ImageStruct.h"
#include "webcore/image/basic/WebCoreImage.h"
#include "webcore/image/basic/EdgeDetection.h"
#include "webcore/image/basic/FeatureDetection.h"
namespace utilsclear{
    int clearImageStruct(ImageStruct* prt);
    int clearWebCoreImage(webcoreimage::CoreImage* prt);
    int clearMat(cv::Mat* prt);
    int clearEdgeDetector(edgedetection::EdgeDetection* prt);
    int clearFeatureDetector(featuredetection::FeatureDetector* prt);
};

#endif // CLEAR_H