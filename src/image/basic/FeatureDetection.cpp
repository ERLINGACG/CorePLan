#include "webcore/image/basic/FeatureDetection.h"
using namespace featuredetection;
FeatureDetector::FeatureDetector(webcoreimage::CoreImage* coreImage){
    this->coreImage = coreImage;
}

std::unique_ptr<ImageStruct> FeatureDetector::FastSURF(
                unsigned char*  image,
                int             size,
                const char*     imagecode,
                int             imageQos
){
     std::vector<unsigned char> buf(image, image + size);
     cv::Mat input = cv::imdecode(buf,cv::IMREAD_GRAYSCALE); // 直接解码为灰度图

     auto detector = cv::xfeatures2d::SURF::create(400); // 创建SURF对象，阈值400

     std::vector<cv::KeyPoint> keypoints;
     detector->detect(input, keypoints); // 计算特征点

     // 绘制特征点
     cv::Mat output;
     cv::drawKeypoints(input, keypoints, output, cv::Scalar::all(-1), 
                        cv::DrawMatchesFlags::DRAW_RICH_KEYPOINTS);
                        
     std::vector<int> params {cv::IMWRITE_JPEG_QUALITY, imageQos};
     std::vector<uchar> encoded;
     cv::imencode(imagecode, output, encoded, params);

    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();
    
    return result;



}
std::unique_ptr<ImageStruct> FeatureDetector::CompareSURF(
                unsigned char*  image,
                int             size,
                unsigned char*  templateImage,
                int             templateSize,
                const char*     imagecode,
                int             imageQos,
                FeatureMatch&   featureMatch

){

   std::vector<unsigned char> buf(image, image + size);
   std::vector<unsigned char> tplBuf(templateImage, templateImage + templateSize);

    cv::Mat imgInput, tplInput;
    cv::imdecode(buf, cv::IMREAD_GRAYSCALE).copyTo(imgInput);
    cv::imdecode(tplBuf, cv::IMREAD_GRAYSCALE).copyTo(tplInput);

     // 创建SURF检测器
    auto detector = cv::xfeatures2d::SURF::create(300);// 高阈值，检测少量强特征
    detector->setNOctaves(4);       // 增加金字塔层数
    detector->setNOctaveLayers(3);  // 增加每层子级数

      // 提取双图特征点和描述子
    std::vector<cv::KeyPoint> kp1, kp2;
    cv::Mat desc1, desc2;
    detector->detectAndCompute(imgInput, cv::noArray(), kp1, desc1);
    detector->detectAndCompute(tplInput, cv::noArray(), kp2, desc2);

      // 执行特征匹配
    cv::BFMatcher matcher(cv::NORM_L2);
    std::vector<std::vector<cv::DMatch>> knn_matches;
    matcher.knnMatch(desc1, desc2, knn_matches, 2); // KNN最近邻匹配

    cv::Mat output;
    cv::cvtColor(imgInput, output, cv::COLOR_GRAY2BGR); // 转换为彩色图
         
     // 筛选优质匹配点（添加距离阈值过滤）
    std::vector<cv::DMatch> good_matches;
    for (size_t i = 0; i < knn_matches.size(); i++) {
         // 比较最近邻匹配和次近邻匹配的距离比值
        if (knn_matches[i][0].distance < 0.7 * knn_matches[i][1].distance) {
            good_matches.push_back(knn_matches[i][0]);
        }
    } 


    // 计算单应性矩阵（需要至少4个匹配点）
    cv::Mat homography;
    if(good_matches.size() >= 4){
        std::vector<cv::Point2f> good_src, good_dst;
        for(const auto& m : good_matches){
            good_src.push_back(kp1[m.queryIdx].pt);
            good_dst.push_back(kp2[m.trainIdx].pt);
        }
        cv::Mat mask;  // 新增掩码矩阵
        homography = cv::findHomography(good_dst, good_src, cv::RANSAC,
                              1.0, mask, 5000, 0.99);

        // 绘制匹配区域（当找到有效单应性矩阵时）
        if(!homography.empty()){
            // 获取模板图像角点
            std::vector<cv::Point2f> tpl_corners(4);
            tpl_corners[0] = cv::Point2f(0, 0);
            tpl_corners[1] = cv::Point2f((float)tplInput.cols, 0);
            tpl_corners[2] = cv::Point2f((float)tplInput.cols, (float)tplInput.rows);
            tpl_corners[3] = cv::Point2f(0, (float)tplInput.rows);
            
            // 转换角点到原始图像坐标
            std::vector<cv::Point2f> obj_corners(4);
            cv::perspectiveTransform(tpl_corners, obj_corners, homography);

            // 绘制多边形边界框
            cv::line(output, obj_corners[0], obj_corners[1], cv::Scalar(0, 255, 0), 2);
            cv::line(output, obj_corners[1], obj_corners[2], cv::Scalar(0, 255, 0), 2);
            cv::line(output, obj_corners[2], obj_corners[3], cv::Scalar(0, 255, 0), 2);
            cv::line(output, obj_corners[3], obj_corners[0], cv::Scalar(0, 255, 0), 2);
            cv::Rect boundingRect = cv::boundingRect(obj_corners);
            std::cout << "匹配区域坐标: (" << boundingRect.x << "," << boundingRect.y 
            << ") 尺寸: " << boundingRect.width << "x" << boundingRect.height << std::endl;
            int inliers = cv::countNonZero(mask); // 内点数量

            float confidence = (float)inliers / good_matches.size();
            featureMatch.confidence=confidence;
            featureMatch.isVerified=1;
            if(!good_matches.empty()) {
                featureMatch.distance = good_matches[0].distance; // 确保使用有效匹配数据
            }
            std::cout << "匹配置信度: " << confidence * 100 << "% (内点数: " 
              << inliers << "/" << good_matches.size() << ")" << std::endl;
            

        }else{
            featureMatch.isVerified=0;
        }
    }
  

     // 编码并返回结果图像
    std::vector<uchar> encoded;
    cv::imencode(imagecode, output, encoded, {cv::IMWRITE_JPEG_QUALITY, imageQos});
    
    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();


    return result;
  
}

std::unique_ptr<ImageStruct> FeatureDetector::CompareORB( //比较特征点
                unsigned char*  image,
                int             size,
                unsigned char*  templateImage,
                int             templateSize,
                const char*     imagecode,
                int             imageQos, 
                FeatureMatch&   featureMatch
){
    std::cout<<"CompareORB()"<<std::endl;
    std::vector<unsigned char> buf(image, image + size);
   std::vector<unsigned char> tplBuf(templateImage, templateImage + templateSize);

    cv::Mat imgInput, tplInput;
    cv::imdecode(buf, cv::IMREAD_GRAYSCALE).copyTo(imgInput);
    cv::imdecode(tplBuf, cv::IMREAD_GRAYSCALE).copyTo(tplInput);

    // 创建ORB检测器（参数根据实际需求调整）
    auto detector = cv::ORB::create(5000, 1.2f, 4); // 特征点数/尺度因子/金字塔层级
    detector->setFastThreshold(20); // 快速阈值

     // 提取特征点和描述子
    std::vector<cv::KeyPoint> kp1, kp2;
    cv::Mat desc1, desc2;
    detector->detectAndCompute(imgInput, cv::noArray(), kp1, desc1);
    detector->detectAndCompute(tplInput, cv::noArray(), kp2, desc2);

    // 特征匹配（使用汉明距离）
    cv::BFMatcher matcher(cv::NORM_HAMMING);
    std::vector<std::vector<cv::DMatch>> knn_matches;
    matcher.knnMatch(desc1, desc2, knn_matches, 2);

    cv::Mat output;
    cv::cvtColor(imgInput, output, cv::COLOR_GRAY2BGR);



    // 筛选优质匹配（调整阈值）
    std::vector<cv::DMatch> good_matches;
    for (size_t i = 0; i < knn_matches.size(); i++) {
        if (knn_matches[i][0].distance < 0.75 * knn_matches[i][1].distance) {
            good_matches.push_back(knn_matches[i][0]);
        }
    }

     // 计算单应性矩阵（需要至少4个匹配点）
    cv::Mat homography;
    if(good_matches.size() >= 4){
        std::vector<cv::Point2f> good_src, good_dst;
        for(const auto& m : good_matches){
            good_src.push_back(kp1[m.queryIdx].pt);
            good_dst.push_back(kp2[m.trainIdx].pt);
        }
        cv::Mat mask;  // 新增掩码矩阵
        homography = cv::findHomography(good_dst, good_src, cv::RANSAC,
                              1.0, mask, 5000, 0.99);

        // 绘制匹配区域（当找到有效单应性矩阵时）
        if(!homography.empty()){
            // 获取模板图像角点
            std::vector<cv::Point2f> tpl_corners(4);
            tpl_corners[0] = cv::Point2f(0, 0);
            tpl_corners[1] = cv::Point2f((float)tplInput.cols, 0);
            tpl_corners[2] = cv::Point2f((float)tplInput.cols, (float)tplInput.rows);
            tpl_corners[3] = cv::Point2f(0, (float)tplInput.rows);
            
            // 转换角点到原始图像坐标
            std::vector<cv::Point2f> obj_corners(4);
            cv::perspectiveTransform(tpl_corners, obj_corners, homography);

            // 绘制多边形边界框
            cv::line(output, obj_corners[0], obj_corners[1], cv::Scalar(0, 255, 0), 2);
            cv::line(output, obj_corners[1], obj_corners[2], cv::Scalar(0, 255, 0), 2);
            cv::line(output, obj_corners[2], obj_corners[3], cv::Scalar(0, 255, 0), 2);
            cv::line(output, obj_corners[3], obj_corners[0], cv::Scalar(0, 255, 0), 2);
            cv::Rect boundingRect = cv::boundingRect(obj_corners);
            std::cout << "匹配区域坐标: (" << boundingRect.x << "," << boundingRect.y 
            << ") 尺寸: " << boundingRect.width << "x" << boundingRect.height << std::endl;
            int inliers = cv::countNonZero(mask); // 内点数量

            float confidence = (float)inliers / good_matches.size();
            featureMatch.confidence=confidence;
            featureMatch.isVerified=1;
            if(!good_matches.empty()) {
                featureMatch.distance = good_matches[0].distance; // 确保使用有效匹配数据
            }
            std::cout << "匹配置信度: " << confidence * 100 << "% (内点数: " 
              << inliers << "/" << good_matches.size() << ")" << std::endl;
            

        }else{
            featureMatch.isVerified=0;
        }
    }
  

     // 编码并返回结果图像
    std::vector<uchar> encoded;
    cv::imencode(imagecode, output, encoded, {cv::IMWRITE_JPEG_QUALITY, imageQos});
    
    auto result = std::make_unique<ImageStruct>();
    result->data.reset(new uchar[encoded.size()]);
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();


    return result;
}

