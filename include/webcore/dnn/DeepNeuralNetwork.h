#ifndef DEEP_NEURAL_NETWORK_H
#define DEEP_NEURAL_NETWORK_H

#include "webcore/image/basic/WebCoreImage.h"
#include "webcore/data/ImageStruct.h"
#include <opencv2/dnn.hpp>
using namespace cv;
using namespace std;
using namespace dnn;
namespace deepneuralnetwork{
    class DeepNeuralNetworkYolo{
        private:
            Net net;
            const char* path;
            webcoreimage::CoreImage* coreImage;
            
            std::vector<string> classNames = {
                "person", "bicycle", "car", "motorcycle", "airplane", "bus", "train", "truck", "boat", "traffic light",
                "fire hydrant", "stop sign", "parking meter", "bench", "bird", "cat", "dog", "horse", "sheep", "cow",
                "elephant", "bear", "zebra", "giraffe", "backpack", "umbrella", "handbag", "tie", "suitcase", "frisbee",
                "skis", "snowboard", "sports ball", "kite", "baseball bat", "baseball glove", "skateboard", "surfboard",
                "tennis racket", "bottle", "wine glass", "cup", "fork", "knife", "spoon", "bowl", "banana", "apple",
                "sandwich", "orange", "broccoli", "carrot", "hot dog", "pizza", "donut", "cake", "chair", "couch",
                "potted plant", "bed", "dining table", "toilet", "tv", "laptop", "mouse", "remote", "keyboard", "cell phone",
                "microwave", "oven", "toaster", "sink", "refrigerator", "book", "clock", "vase", "scissors", "teddy bear",
                "hair drier", "toothbrush"
            };
        public:
            DeepNeuralNetworkYolo(
                webcoreimage::CoreImage* coreImage,
                const char* path
            );
            int ReadNetFromONNX();
            ~DeepNeuralNetworkYolo()=default;
            std::unique_ptr<ImageStruct> DetectImage(
                unsigned char*  image,
                int             size,
                const char*     imagecode,
                int             imageQos
            );
            Mat ForMat(const Mat& source);
        };
}



#endif