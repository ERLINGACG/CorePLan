#include "webcore/WebCore.h"
#include "webcore/dnn/DeepNeuralNetwork.h"
using namespace deepneuralnetwork;
extern "C" {
    CORE_API DeepNeuralNetworkYolo* CreateDNN_Yolo(
        webcoreimage::CoreImage* coreImage,
        const char* model_path
    ){
        return new DeepNeuralNetworkYolo(coreImage, model_path);
    }
    CORE_API int ReadNetFromONNX(DeepNeuralNetworkYolo* dnn){
        return dnn->ReadNetFromONNX();
    }
    CORE_API ImageStruct* YoloDetectImage(
        DeepNeuralNetworkYolo* dnn,
        unsigned char*  image,
        int             size,
        const char*     imagecode,
        int             imageQos
    ){
        return dnn->DetectImage(
            image, size, imagecode, imageQos
        ).release();
    }
}