#include "webcore/WebCore.h"
using namespace webcoreimage;
using namespace featuredetection;

extern "C"  {
    CORE_API FeatureDetector* CreateFeatureDetector(webcoreimage::CoreImage* coreImage) {
        return new FeatureDetector(coreImage);
    }
}
extern "C"  {
    CORE_API ImageStruct* FastSURF(
       FeatureDetector* featureDetector,
        unsigned char*  image,
        int             size,
        const char*     imagecode,
        int             imageQos
    ){
        return featureDetector->FastSURF(
            image,
            size,
            imagecode,
            imageQos
        ).release();
    }
    CORE_API ImageStruct* CompareSURF(
        FeatureDetector* featureDetector,
        unsigned char*  image,
        int             size,
        unsigned char*  templateImage,
        int             templateSize,
        const char*     imagecode,
        int             imageQos,
        FeatureMatch&   featureMatch
    ){
      return featureDetector->CompareSURF(
            image,
            size,
            templateImage,
            templateSize,
            imagecode,
            imageQos,
            featureMatch
        ).release();
    }

    CORE_API ImageStruct* CompareORB(
        FeatureDetector* featureDetector,
        unsigned char*  image,
        int             size,
        unsigned char*  templateImage,
        int             templateSize,
        const char*     imagecode,
        int             imageQos,
        FeatureMatch&   featureMatch
    ){
      return featureDetector->CompareORB(
            image,
            size,
            templateImage,
            templateSize,
            imagecode,
            imageQos,
            featureMatch
        ).release();
    }
}