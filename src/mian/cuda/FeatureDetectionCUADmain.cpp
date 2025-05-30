#include "webcore/image/cuda/FeatureDetectionCUAD.h"
#include "webcore/WebCore.h"
using namespace featuredetectioncuad;

extern "C"  {
    CORE_API FeatureDetectionCUAD* CreateFeatureDetectorCUAD(webcoreimage::CoreImage* coreImage) {
        return new FeatureDetectionCUAD(coreImage);
    }
    CORE_API ImageStruct* CUAD_FastSURF(
        FeatureDetectionCUAD* FeatureDetectionCUAD,
        unsigned char*  image,
        int             size,
        const char*     imagecode,
        int             imageQos 
    ){
        return FeatureDetectionCUAD->CUDA_FastUSURF(
            image,
            size,
            imagecode,
            imageQos
        ).release();
    }


}