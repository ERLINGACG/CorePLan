#ifndef FEATUREDATA_H
#define FEATUREDATA_H

#include <vector>

    struct FeaturePoint
    {
            float x;     //x坐标
            float y;    //y坐标
            float size; //大小
            float response; //响应值
            float angle;  //角度
    };
    struct FeatureMatch 
    {

        float distance;            // 特征距离
        float confidence;          // 置信度
        int   isVerified;          // 是否通过验证
    };
    


#endif