#include <iostream>
#include  "opencv2/opencv.hpp"
#include <vector>
#include "opencv2/xfeatures2d.hpp"
#define TEST_EXPORT __declspec(dllexport)


cv::Mat openImage(std::string path){ //打开原始图像
        cv::Mat img = cv::imread(path);
        return img;
}
cv::Mat openImageChar(unsigned char* data,int size){ //打开原始图像
       std::vector<unsigned char> buf(data, data + size);
        cv::Mat img = cv::imdecode(buf, cv::IMREAD_COLOR);
        return img;

}

extern "C" TEST_EXPORT unsigned char* openImage(int* outWidth, int* outHeight, int* outSize){

    cv::Mat img = openImage("E:/CorePLAN/Web/WebCore/WebCoreOpenCV/build/Debug/image/Sample1.png");
     // 转换为连续内存
    if (!img.isContinuous()) {
        img = img.clone();
    }
      // 编码为JPG格式
    std::vector<unsigned char> buf;
    cv::imencode(".jpg", img, buf);

    // 分配持久内存
    unsigned char* data = new unsigned char[buf.size()];
    memcpy(data, buf.data(), buf.size());

    
      // 返回图像参数
    *outWidth = img.cols;
    *outHeight = img.rows;
    *outSize = static_cast<int>(buf.size());

    return data;
}

extern "C" TEST_EXPORT unsigned char* SURFJ(
  unsigned char* data,
  int size,
  int* outWidth, 
  int* outHeight, 
  int* outSize){
    cv::Mat img = openImageChar(data,size);
     if(img.empty()) return nullptr;
      // SURF特征检测
    std::vector<cv::KeyPoint> keypoints;
    cv::Mat descriptors;

    auto surf = cv::xfeatures2d::SURF::create(400); // 阈值设为400
     // 检测并计算
    surf->detectAndCompute(img, cv::noArray(), keypoints, descriptors);

      // 绘制关键点
    cv::Mat outputImg;
    cv::drawKeypoints(img, keypoints, outputImg, cv::Scalar::all(-1), 
                     cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
     // 编码为JPG返回
    std::vector<unsigned char> buf;
    cv::imencode(".jpg", outputImg, buf);
    // 分配内存并拷贝数据
    unsigned char* result = new unsigned char[buf.size()];
    memcpy(result, buf.data(), buf.size());

      // 设置输出参数
    *outWidth = outputImg.cols;
    *outHeight = outputImg.rows; 
    *outSize = static_cast<int>(buf.size());
    
    return result;
}
extern "C" TEST_EXPORT void releaseImage(unsigned char* data){
    delete[] data;
}
