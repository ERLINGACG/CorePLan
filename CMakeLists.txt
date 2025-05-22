cmake_minimum_required(VERSION 3.10)
project(WebCoreOpenCVT)


# 新增编码设置
add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")


include_directories(./include/)
link_directories(./lib/)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})
if(WIN32)
    link_directories(${JNI_LIBRARY_DIRS})
endif()


# add_executable(WebCoreOpenCV src/main.cpp)
add_library(
    WebCoreOpenCVT SHARED 
    
    
    src/mian/basic/image/ImageMian.cpp
    src/mian/basic/image/EdgeDetectionMian.cpp
    src/mian/basic/utils/ClearUtils.cpp


    src/image/basic/WebCoreImage.cpp
    src/image/basic/EdgeDetection.cpp
    src/utils/Clear.cpp

)
add_library(
    WebCoreJNI SHARED
    src/mian/basic/image/EdgeDetectionMianJNI.cpp

    src/image/basic/WebCoreImage.cpp
    src/image/basic/EdgeDetection.cpp
)


target_link_libraries(
    WebCoreOpenCVT 
    opencv_world4120
)
target_link_libraries(
    WebCoreJNI
    ${JNI_LIBRARIES}
    opencv_world4120
)


if(MSVC)
    add_compile_options(/O2 /GL)         # 启用优化和全程序优化
    add_link_options(/LTCG /DEBUG:FULL) # 链接时代码生成和完整调试信息
    add_compile_options(/arch:AVX2)     # 启用AVX2指令集
else()
    add_compile_options(-Os)
endif()
