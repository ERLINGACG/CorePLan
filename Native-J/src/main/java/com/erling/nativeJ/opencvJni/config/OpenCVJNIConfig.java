package com.erling.nativeJ.opencvJni.config;


import lombok.Getter;

@Getter
public enum OpenCVJNIConfig {
    OPENCV_VERSION_4_12_0("4.12.0","E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\WebCoreJNI.dll"),;

    private final String libPath;
    private final String version;

    OpenCVJNIConfig(String version, String libPath) {
        this.libPath = libPath;
        this.version = version;
    }
}
