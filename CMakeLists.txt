cmake_minimum_required(VERSION 3.10)
project(WebCoreOpenCVT)


# 新增编码设置
add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")


include_directories(./include/)
link_directories(./lib/)



# add_executable(WebCoreOpenCV src/main.cpp)
add_library(
    WebCoreOpenCVT SHARED 
    src/main.cpp
    src/image/basic/WebCoreImage.cpp
    src/utils/Clear.cpp

    )


target_link_libraries(
    WebCoreOpenCVT 
    opencv_world4120
)
