#ifndef IMAGESTRUCT_H
#define IMAGESTRUCT_H
#include <memory>
struct ImageStruct
{
    int width; // 图像宽度
    int height;// 图像高度
    int channels;// 图像通道数
    int64_t size;   // 图像大小
    // 使用智能指针包装数据
    std::unique_ptr<unsigned char[]> data;
    // 构造函数和析构函数
     ~ImageStruct() = default;
};


#endif // IMAGESTRUCT_H