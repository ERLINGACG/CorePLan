#include "webcore/utils/Clear.h"



int utilsclear::clearImageStruct(ImageStruct* image){
    try{
        delete image;
        return 0;
    }catch(...){
        return -1;
    }
}
int utilsclear::clearWebCoreImage(webcoreimage::CoreImage* prt){
    try{
        delete prt;
        return 0;
    }catch(...){
        return -1;
    }
}
int utilsclear::clearMat(cv::Mat* prt){
     try{
        delete prt;
        return 0;
    }catch(...){
        return -1;
    }
}
int utilsclear::clearEdgeDetector(edgedetection::EdgeDetection* prt){
    try{
        delete prt;
        return 0;
    }catch(...){
        return -1;
    }
}
