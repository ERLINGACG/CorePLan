#include "webcoreJNI/image/com_erling_nativeJ_opencvJni_libinterface_basic_image__EdgeDetectionJni.h"
#include <opencv2/opencv.hpp>
#include <chrono>

JNIEXPORT jbyteArray JNICALL Java_com_erling_nativeJ_opencvJni_libinterface_basic_image__1EdgeDetectionJni_SobelJNI
(JNIEnv* env, jobject obj, 
   jbyteArray img, 
   jstring imageCode, 
   jint gauss_size, 
   jdouble sigmaX,
   jint ksize, 
   jdouble scale, 
   jdouble delta, 
   jint imageQos) 
{
   try{
        // 获取ByteBuffer数据
        jbyte* buffer = env->GetByteArrayElements(img, nullptr);
        jsize length = env->GetArrayLength(img);
        
        // 图像解码
        cv::Mat input = cv::imdecode(cv::Mat(1, length, CV_8UC1, buffer), cv::IMREAD_GRAYSCALE);
        env->ReleaseByteArrayElements(img, buffer, JNI_ABORT);  // 释放内存

        
        // 高斯滤波
        cv::Mat blurred;
        cv::GaussianBlur(input, blurred, cv::Size(gauss_size, gauss_size), sigmaX, sigmaX);

         // Sobel边缘检测
        cv::Mat grad_x, grad_y;
        cv::Sobel(blurred, grad_x, CV_16S, 1, 0, ksize, scale, delta, cv::BORDER_DEFAULT);
        cv::Sobel(blurred, grad_y, CV_16S, 0, 1, ksize, scale, delta, cv::BORDER_DEFAULT);
        cv::convertScaleAbs(grad_x, grad_x);
        cv::convertScaleAbs(grad_y, grad_y);

        cv::Mat edges;
        cv::addWeighted(grad_x, 0.5, grad_y, 0.5, 0, edges);

        const char* code = env->GetStringUTFChars(imageCode, nullptr);
        std::vector<uchar> encodedBuf;
        cv::imencode(std::string(code), edges, encodedBuf, 
            {cv::IMWRITE_WEBP_QUALITY, imageQos});
        env->ReleaseStringUTFChars(imageCode, code);

         // 返回byte数组
        jbyteArray result = env->NewByteArray(encodedBuf.size());
        env->SetByteArrayRegion(result, 0, encodedBuf.size(), 
                             reinterpret_cast<jbyte*>(encodedBuf.data()));
        return result;

   }catch(...){
        env->ThrowNew(env->FindClass("java/lang/RuntimeException"), 
                    "Sobel边缘检测失败");
   }
   return nullptr;
}