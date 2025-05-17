#ifndef CLEAR_H
#define CLEAR_H
#include "webcore/data/ImageStruct.h"
#include "webcore/image/basic/WebCoreImage.h"

namespace utilsclear{
    int clearImageStruct(ImageStruct* prt);
    int clearWebCoreImage(webcoreimage::CoreImage* prt);
    int clearMat(cv::Mat* prt);
};

#endif // CLEAR_H