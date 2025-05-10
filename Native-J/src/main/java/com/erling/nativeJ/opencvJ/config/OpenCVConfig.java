package com.erling.nativeJ.opencvJ.config;

import lombok.Getter;


@Getter
public enum OpenCVConfig {


    OPENCV_VERSION_4_12_0("4.12.0","lib\\WebCoreOpenCV.dll"),

    OPENCV_VERSION_4_12_0D("4.12.0D","E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\WebCoreOpenCVT.dll");


    private final String libPath;
    private final String version;
    OpenCVConfig(String version, String libPath) {
        this.version = version;
        this.libPath = libPath;
    }


}
