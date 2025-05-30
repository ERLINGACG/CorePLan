#include "webcore/image/cuda/FeatureDetectionCUAD.h"
#include "opencv2/xfeatures2d/cuda.hpp"  // 新增SURF_CUDA专用头文件
using namespace featuredetectioncuad;

FeatureDetectionCUAD::FeatureDetectionCUAD(webcoreimage::CoreImage* coreImage) {
    this->coreImage = coreImage;
}
std::unique_ptr<ImageStruct> FeatureDetectionCUAD::CUDA_FastUSURF(
                unsigned char*  image,
                int             size,
                const char*     imagecode,
                int             imageQos 
){ 
    std::vector<unsigned char> buf(image, image + size);
    cv::Mat input = cv::imdecode(cv::Mat(1, size, CV_8UC1, image), cv::IMREAD_GRAYSCALE);
     // CUDA 加速实现
    cv::cuda::GpuMat gpu_img;
    // cv::Mat gray;
    
    // // 转换为灰度图并上传到GPU
    // cv::cvtColor(input, gray, cv::COLOR_BGR2GRAY);
    gpu_img.upload(input);

    // 创建CUDA SURF检测器
    auto detector = cv::cuda::SURF_CUDA::create(400);

     // GPU内存存储关键点
    cv::cuda::GpuMat keypoints_gpu;
    cv::cuda::GpuMat descriptors_gpu;

      // 执行特征检测
    detector->detectWithDescriptors(gpu_img, cv::cuda::GpuMat(), keypoints_gpu, descriptors_gpu);

// 下载关键点到CPU
    std::vector<cv::KeyPoint> keypoints;
    detector->downloadKeypoints(keypoints_gpu, keypoints);

     // 绘制特征点（在原始彩色图像上）
    cv::Mat output;
    cv::drawKeypoints(input, keypoints, output, cv::Scalar::all(-1), 
                     cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);

    // 编码返回（与CPU版本相同）
    std::vector<int> params {cv::IMWRITE_JPEG_QUALITY, imageQos};
    std::vector<uchar> encoded;
    cv::imencode(imagecode, output, encoded, params);

    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();
    
    return result;

}
