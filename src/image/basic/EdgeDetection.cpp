#include "webcore/image/basic/EdgeDetection.h"

using namespace edgedetection;

EdgeDetection::EdgeDetection(webcoreimage::CoreImage* coreImage) 
    : coreImage(coreImage)  // 成员初始化列表
{

}


std::unique_ptr<ImageStruct> EdgeDetection::Canny(
            cv::Mat* image,         // 输入图像
            double threshold1,      // 低阈值
            double threshold2,      // 高阈值 
            int  imageqos
){
    if(!image || image->empty()){  // 添加空指针检查
        return nullptr;
    }
    // 确保内存连续
    if(!image->isContinuous()) {
        *image = image->clone();
    }
    cv::Mat gray;
    if(image->channels() > 1) {
        cv::cvtColor(*image, gray, cv::COLOR_BGR2GRAY); //转换为灰度图
    } else {
        gray = *image;
    }
    if(!gray.isContinuous()) {
        gray = gray.clone();
    }

    cv::Mat edges;
    cv::Canny(*image, edges, threshold1, threshold2);  // Canny边缘检测
    std::vector<unsigned char> encodedBuf;
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, imageqos}; // 添加编码参数

    if(!cv::imencode(".webp", edges, encodedBuf, params)) {
        std::cerr << "图像编码失败，尺寸：" 
                  << edges.cols << "x" << edges.rows  // 修改为edges的尺寸
                  << " 类型：" << edges.type()
                  << " 通道数：" << edges.channels() << std::endl;
        return nullptr;
    }
    auto edges_ptr = std::make_unique<cv::Mat>(edges);
    return this->coreImage->MatforImage(edges_ptr.get());

}
std::unique_ptr<ImageStruct> EdgeDetection::InlineCanny(
            unsigned char*  image,
            int              size,
            const char* imagecode,
            double      threshold1, 
            double      threshold2,
            int         imageqos
){
    std::vector<unsigned char> buf(image, image + size);
    cv::Mat input = cv::imdecode(buf, cv::IMREAD_GRAYSCALE); // 直接解码为灰度图
    
    // 单次处理流程
    cv::Mat edges;
    cv::Canny(input, edges, threshold1, threshold2);
    
    // 降低编码质量
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, imageqos}; 
    std::vector<uchar> encoded;
    cv::imencode(imagecode, edges, encoded, params);
    
    // 直接构造结果
    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();
    
    return result;
}



std::unique_ptr<ImageStruct> EdgeDetection::Laplacian(
            cv::Mat* image,
            double scale,       // 缩放比例
            double delta,       // 阈值
            int imageqos
){
    if(!image || image->empty()){  
        return nullptr;
    }
    // 确保内存连续
    if(!image->isContinuous()) {
        *image = image->clone();
    }
    cv::Mat gray;
    if(image->channels() > 1) {
        cv::cvtColor(*image, gray, cv::COLOR_BGR2GRAY);
    } else {
        gray = *image;
    }
    cv::Mat laplacian;
    cv::Laplacian(gray, laplacian, CV_16S, 3, scale, delta, cv::BORDER_DEFAULT); // 计算拉普拉斯算子
    cv::convertScaleAbs(laplacian, laplacian); // 转换为绝对值
    std::vector<unsigned char> encodedBuf;
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, imageqos}; // 添加编码参数
    if(!cv::imencode(".webp", laplacian, encodedBuf, params)) {
         std::cerr << "图像编码失败，尺寸：" 
                   << laplacian.cols << "x" << laplacian.rows   
                   << " 类型：" << laplacian.type()
                   << " 通道数：" << laplacian.channels() << std::endl;
         return nullptr;
    }
    auto laplacian_ptr = std::make_unique<cv::Mat>(laplacian);
    return this->coreImage->MatforImage(laplacian_ptr.get());

}
 std::unique_ptr<ImageStruct> EdgeDetection::InlineLaplacian(
            unsigned char*  image,
            int             size,
            const char*     imagecode,
            double          scale,       // 缩放比例
            double          delta,       // 阈值
            int             imageqos
){
    std::vector<unsigned char> buf(image, image + size);
    cv::Mat input = cv::imdecode(buf, cv::IMREAD_GRAYSCALE); // 直接解码为灰度图
    
    cv::Mat laplacian;
    cv::Laplacian(input, laplacian, CV_16S, 3, scale, delta, cv::BORDER_DEFAULT); // 计算拉普拉斯算子
    cv::convertScaleAbs(laplacian, laplacian); // 转换为绝对值

    
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, imageqos}; 
    std::vector<uchar> encoded;
    cv::imencode(imagecode, laplacian, encoded, params);
    
    // 直接构造结果
    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();
    
    return result;
        
}
std::unique_ptr<ImageStruct> EdgeDetection::InlineSobel(
            unsigned char*  image,
            int             size,
            const char*     imagecode,
            int             gauss_size ,      // 高斯滤波器大小
            double          sigmaX     ,          // x方向标准差
            int             ksize      , 
            double          scale      ,   // 缩放因子
            double          delta      ,   // 增量值 
            int             imageqos   
){
     if(gauss_size > 0) {
        gauss_size |= 0x1;  // 奇数化：当gauss_size为偶数时，将其+1
     } else {
        gauss_size = 1;     // 处理非正数的情况
     }
     std::vector<unsigned char> buf(image, image + size);
     cv::Mat input = cv::imdecode(buf, cv::IMREAD_GRAYSCALE); // 直接解码为灰度图
    //  cv::resize(input, input, cv::Size(800,600),0,0, cv::INTER_AREA); // 缩放
     cv::Mat grad_x, grad_y; //x方向梯度，y方向梯度
     cv::GaussianBlur(input, input,cv::Size(gauss_size,gauss_size), sigmaX);    //高斯滤波,原地操作
     cv::Sobel(input, grad_x, CV_16S, 1, 0, ksize, scale, delta, cv::BORDER_DEFAULT); //x方向梯度
     cv::Sobel(input, grad_y, CV_16S, 0, 1, ksize, scale, delta, cv::BORDER_DEFAULT); //y方向梯度

     //转换回CV_8U类型
    cv::convertScaleAbs(grad_x, grad_x);
    cv::convertScaleAbs(grad_y, grad_y);

    cv::Mat combined;
    cv::addWeighted(grad_x, 0.5, grad_y, 0.5, 0, combined);
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, imageqos}; 
    std::vector<uchar> encoded;
    cv::imencode(imagecode, combined, encoded, params);

    // 直接构造结果
    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();
    
    return result;
}

std::unique_ptr<ImageStruct> EdgeDetection::Sobel(
    cv::Mat* image,
    int      gauss_size,      // 高斯滤波器大小
    double   sigmaX,          // x方向标准差
    int      ksize, 
    double   scale,   // 缩放因子 
    double   delta,   // 增量值
    int      imageqos
){
    if(!image || image->empty()){  
        return nullptr;
    }
    // 确保内存连续
    if(!image->isContinuous()) {
        *image = image->clone();
    }
    cv::Mat gray, grad_x, grad_y; //x方向梯度，y方向梯度
    if(image->channels() > 1) {
        cv::cvtColor(*image, gray, cv::COLOR_BGR2GRAY);
    } else {
        gray = *image;
    }
    cv::GaussianBlur(gray, gray, cv::Size(gauss_size,gauss_size), sigmaX);    //高斯滤波
    cv::Sobel(gray, grad_x, CV_16S, 1, 0, ksize, scale, delta, cv::BORDER_DEFAULT); //x方向梯度
    cv::Sobel(gray, grad_y, CV_16S, 0, 1, ksize, scale, delta, cv::BORDER_DEFAULT); //y方向梯度

    //转换回CV_8U类型
    cv::convertScaleAbs(grad_x, grad_x);
    cv::convertScaleAbs(grad_y, grad_y);

    cv::Mat combined;
    cv::addWeighted(grad_x, 0.5, grad_y, 0.5, 0, combined);
    std::vector<unsigned char> encodedBuf;
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, imageqos}; // 添加编码参数
    if(!cv::imencode(".webp", combined, encodedBuf, params)) {
         std::cerr << "图像编码失败，尺寸：" 
                   << combined.cols << "x" << combined.rows   
                   << " 类型：" << combined.type()
                   << " 通道数：" << combined.channels() << std::endl;
         return nullptr;
    }
    auto combined_ptr = std::make_unique<cv::Mat>(combined);
    return this->coreImage->MatforImage(combined_ptr.get());
}