#include "webcoreJNI/test/com_erling_nativeJ_opencvJni_libinterface_basic_EdgeDetectionJni.h"
#include "webcore/image/basic/EdgeDetection.h"
#include "opencv2/cudaimgproc.hpp"
#include "opencv2/cudabgsegm.hpp"
#include "opencv2/core/cuda.hpp"
#include <chrono>  // 添加时间测量头文件
using namespace edgedetection;
JNIEXPORT jint JNICALL Java_com_erling_nativeJ_opencvJni_libinterface_basic_EdgeDetectionJni_ADD
  (JNIEnv* env, jclass clazz, jint a, jint b){
    return a+b;
  }

JNIEXPORT jbyteArray JNICALL Java_com_erling_nativeJ_opencvJni_libinterface_basic_EdgeDetectionJni_TestCanny
  (JNIEnv* env, jobject obj,jbyteArray img)
  {
    try {
        cv::cuda::printCudaDeviceInfo(cv::cuda::getDevice());
        if (!cv::cuda::getCudaEnabledDeviceCount()) {
            std::cerr << "CUDA设备不可用" << std::endl;
        }else{
            std::cout << "CUDA设备可用" << std::endl;
        }
        auto total_start = std::chrono::high_resolution_clock::now();
        
        // [1] 数据接收阶段
        auto t1 = std::chrono::high_resolution_clock::now();
        jbyte* buffer = env->GetByteArrayElements(img, nullptr);
        int length = env->GetArrayLength(img);
        auto t2 = std::chrono::high_resolution_clock::now();

        // [2] 图像解码阶段
        auto t3 = std::chrono::high_resolution_clock::now();
        cv::cuda::HostMem host_input(cv::cuda::HostMem::PAGE_LOCKED);
        cv::Mat input = host_input.createMatHeader();
        cv::imdecode(cv::Mat(1, length, CV_8UC1, buffer), cv::IMREAD_ANYCOLOR, &input);
        auto t4 = std::chrono::high_resolution_clock::now();


        auto t5 = std::chrono::high_resolution_clock::now();
        
        // [4] GPU处理阶段
        static cv::cuda::GpuMat d_input, d_gray, d_edges;
        static cv::cuda::Stream stream;
        
        // 异步上传并转换颜色空间
        auto t6 = std::chrono::high_resolution_clock::now();
        d_input.upload(input, stream);
        cv::cuda::cvtColor(d_input, d_gray, cv::COLOR_BGR2GRAY, 0, stream);
        auto t7 = std::chrono::high_resolution_clock::now();
        
        // [5] Canny检测
        static cv::Ptr<cv::cuda::CannyEdgeDetector> detector = 
            cv::cuda::createCannyEdgeDetector(50, 150, 3, false);
        auto t8 = std::chrono::high_resolution_clock::now();
        detector->detect(d_gray, d_edges, stream);
        auto t9 = std::chrono::high_resolution_clock::now();
        
        // [6] 下载结果
        cv::Mat edges;
        d_edges.download(edges, stream);
        stream.waitForCompletion();
        auto t10 = std::chrono::high_resolution_clock::now();

        // [7] 编码阶段
        std::vector<uchar> encodedBuf;
        std::vector<int> params {
            cv::IMWRITE_WEBP_QUALITY, 95,        // 质量从95降低到80
            0x1000 | 0x0B, 4,                    // 疑似线程参数魔法数字
            0x1000 | 0x0C, 3                     // 疑似压缩方法魔法数字
        };
        cv::imencode(".webp", edges, encodedBuf, params);
        auto t11 = std::chrono::high_resolution_clock::now();
        
        // 性能分析输出
        auto fmt_duration = [](auto start, auto end) {
             return std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();
        };
        std::cout << "[性能分析] 各阶段耗时(us):\n"
                  << "数据接收: " << fmt_duration(t1, t2) << "\n"
                  << "图像解码: " << fmt_duration(t2, t3) << "\n"
                  << "内存锁定: " << fmt_duration(t4, t5) << "\n"
                  << "GPU上传/转换: " << fmt_duration(t6, t7) << "\n"
                  << "Canny检测: " << fmt_duration(t8, t9) << "\n"
                  << "结果下载: " << fmt_duration(t9, t10) << "\n"
                  << "WEBP编码: " << fmt_duration(t10, t11) << "\n"
                  << "总耗时: " << fmt_duration(total_start, t11) << std::endl;

        // [8] 内存释放和返回
        env->ReleaseByteArrayElements(img, buffer, JNI_ABORT);
        jbyteArray output = env->NewByteArray(encodedBuf.size());
        env->SetByteArrayRegion(output, 0, encodedBuf.size(), 
                              reinterpret_cast<jbyte*>(encodedBuf.data()));
        return output;
    } catch (...) {
        env->ThrowNew(env->FindClass("java/lang/RuntimeException"), 
                    "Canny 边缘检测失败");
    }
    return nullptr;
  }

JNIEXPORT jbyteArray JNICALL Java_com_erling_nativeJ_opencvJni_libinterface_basic_EdgeDetectionJni_TestNoCudaCanny
  (JNIEnv* env, jobject obj,jbyteArray img){
        try{
            auto total_start = std::chrono::high_resolution_clock::now();
            
            // [1] 数据接收阶段
            auto t1 = std::chrono::high_resolution_clock::now();
            jbyte* buffer = env->GetByteArrayElements(img, nullptr);
            int length = env->GetArrayLength(img);
            auto t2 = std::chrono::high_resolution_clock::now();

            // [2] 图像解码阶段
           auto t3 = std::chrono::high_resolution_clock::now();
            // 优化1：直接使用buffer指针避免内存拷贝
           cv::Mat input = cv::imdecode(cv::Mat(1, length, CV_8UC1, buffer), cv::IMREAD_GRAYSCALE); // 直接解码为灰度图
           auto t4 = std::chrono::high_resolution_clock::now();
           
           // [4] CPU处理阶段
           auto t5 = std::chrono::high_resolution_clock::now();
            cv::Mat edges(input.size(), CV_8UC1);
            cv::Canny(input, edges, 50, 150);
            auto t6 = std::chrono::high_resolution_clock::now();
            
            // [6] 编码阶段
            auto t7 = std::chrono::high_resolution_clock::now();
            std::vector<int> params {cv::IMWRITE_WEBP_QUALITY, 95}; 
            static thread_local std::vector<uchar> encoded;
            encoded.clear();
            // 添加二值化处理（减少编码数据量）
            cv::Mat binaryEdges;
            cv::threshold(edges, binaryEdges, 127, 255, cv::THRESH_BINARY);
            cv::imencode(".jpeg", edges, encoded, params);
            auto t8 = std::chrono::high_resolution_clock::now();
            
            // 性能分析输出
            auto fmt_duration = [](auto start, auto end) {
                 return std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();
            };
            std::cout << "[性能分析] 各阶段耗时(us):\n"
                      << "数据接收: " << fmt_duration(t1, t2) << "\n"
                      << "图像解码: " << fmt_duration(t3, t4) << "\n"
                      << "CPUCanny检测处理: " << fmt_duration(t5, t6) << "\n"
                      << "编码耗时: " << fmt_duration(t7, t8) << "\n"
                      << "总耗时: " << fmt_duration(total_start, t8) << std::endl;

            env->ReleaseByteArrayElements(img, buffer, JNI_ABORT);
            jbyteArray output = env->NewByteArray(encoded.size());
            env->SetByteArrayRegion(output, 0, encoded.size(), 
                                 reinterpret_cast<jbyte*>(encoded.data()));
            return output;
 

        }catch(...){
          env->ThrowNew(env->FindClass("java/lang/RuntimeException"), 
                    "Canny 边缘检测失败");
        }
        return nullptr;
  }
