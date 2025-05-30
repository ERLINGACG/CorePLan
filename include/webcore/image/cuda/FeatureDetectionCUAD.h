#ifndef __FEATUREDETECTIONCUAD_H__
#define __FEATUREDETECTIONCUAD_H__
#include "webcore/image/basic/WebCoreImage.h"
#include "webcore/data/ImageStruct.h"
#include "opencv2/cudaimgproc.hpp"
#include "opencv2/cudabgsegm.hpp"
#include "opencv2/core/cuda.hpp"
namespace featuredetectioncuad {
    class FeatureDetectionCUAD {
        private:
            webcoreimage::CoreImage* coreImage;
        public:
            FeatureDetectionCUAD(webcoreimage::CoreImage* coreImage);
            ~FeatureDetectionCUAD()=default;

            std::unique_ptr<ImageStruct> CUDA_FastUSURF(
                unsigned char*  image,
                int             size,
                const char*     imagecode,
                int             imageQos =90
            );
    };
}
#endif // __FEATUREDETECTIONCUAD_H__