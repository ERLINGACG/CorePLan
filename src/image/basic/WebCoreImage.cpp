#include "webcore/image/basic/WebcoreImage.h"
#include "webcore/data/ImageStruct.h"
using namespace webcoreimage;

std::unique_ptr<ImageStruct> CoreImage::openImage(unsigned char* data,int size){
       std::vector<unsigned char> buf(data, data + size); // 复制数据到vector
       cv::Mat img = cv::imdecode(buf, cv::IMREAD_COLOR); // 解码图像数据
        // 新增代码开始
        if(img.empty()) {
            std::cerr << "Failed to decode image" << std::endl;
            return nullptr;
        }
        // 确保内存连续
        if(!img.isContinuous()) {
            img = img.clone();
        }
        auto result = std::make_unique<ImageStruct>();
        result->width = img.cols;
        result->height = img.rows;
        result->channels = img.channels();

        // 使用unique_ptr管理图像数据
        result->data = std::unique_ptr<unsigned char[]>(new unsigned char[buf.size()]);
        memcpy(result->data.get(), buf.data(), buf.size());
        result->size = buf.size();

        return result;
}
std::unique_ptr<cv::Mat> CoreImage::ImageforMat(ImageStruct* image){
     // 添加参数校验
    if(!image || !image->data || image->width <=0 || image->height <=0){
        return nullptr;
    }
    // 将原始字节流解码为Mat（关键修改）
    std::vector<unsigned char> buf(image->data.get(), image->data.get() + image->size);
    cv::Mat decoded = cv::imdecode(buf, cv::IMREAD_COLOR);
    if(decoded.empty()) {
        return nullptr;
    }

    return std::make_unique<cv::Mat>(decoded);
}

std::unique_ptr<ImageStruct> CoreImage::MatforImage(cv::Mat* image){
    if(!image || image->empty()){  // 添加空指针检查
        return nullptr;
    }
    // 确保内存连续
    if(!image->isContinuous()) {
        *image = image->clone();
    }
     // 新增边缘检测处理
    // cv::Mat edges;
    // cv::cvtColor(*image, edges, cv::COLOR_BGR2GRAY);  // 转为灰度图
    // cv::GaussianBlur(edges, edges, cv::Size(3,3), 0);  // 高斯模糊降噪
    // cv::Canny(edges, edges, 50, 150);  // Canny边缘检测


    std::vector<unsigned char> encodedBuf;
    std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, 95}; // 添加编码参数

    if(!cv::imencode(".webp", *image, encodedBuf, params)) {
        std::cerr << "图像编码失败，尺寸：" 
                  << image->cols << "x" << image->rows 
                  << " 类型：" << image->type() 
                  << " 通道数：" << image->channels() << std::endl;
        return nullptr;
    }
    //  if(!cv::imencode(".webp", edges, encodedBuf, params)) {
    //     std::cerr << "图像编码失败，尺寸：" 
    //               << edges.cols << "x" << edges.rows  // 修改为edges的尺寸
    //               << " 类型：" << edges.type()
    //               << " 通道数：" << edges.channels() << std::endl;
    //     return nullptr;
    // }



    auto result = std::make_unique<ImageStruct>();
    result->width = image->cols;
    result->height = image->rows;
    result->channels = image->channels();
    result->size = encodedBuf.size();
    
    // auto result = std::make_unique<ImageStruct>();
    // // 更新为处理后的图像参数
    // result->width = edges.cols;
    // result->height = edges.rows;
    // result->channels = edges.channels();
    // result->size = encodedBuf.size();
    
    result->data = std::unique_ptr<unsigned char[]>(new unsigned char[encodedBuf.size()]);
    memcpy(result->data.get(), encodedBuf.data(), encodedBuf.size());
    
    return result;
}

std::unique_ptr<ImageStruct> CoreImage::Canny(
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
    return this->MatforImage(edges_ptr.get());

}

std::unique_ptr<ImageStruct> CoreImage::Laplacian(
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
    cv::cvtColor(*image, gray, cv::COLOR_BGR2GRAY); //转换为灰度图
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
    return this->MatforImage(laplacian_ptr.get());
     
} 

std::unique_ptr<ImageStruct> CoreImage::Sobel(cv::Mat* image){
  return nullptr;
}

std::unique_ptr<ImageStruct> CoreImage::Scharr(cv::Mat* image){
    return nullptr;
}
