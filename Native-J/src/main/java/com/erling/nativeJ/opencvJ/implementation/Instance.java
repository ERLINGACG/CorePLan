package com.erling.nativeJ.opencvJ.implementation;

import com.erling.nativeJ.opencvJ.config.OpenCVConfig;
import com.erling.nativeJ.opencvJ.libinterface.basic.ImageCore;
import com.erling.nativeJ.opencvJ.libinterface.basic.OpencvJNA;
import com.erling.nativeJ.opencvJ.libinterface.basic.image.EdgeDetection;
import com.erling.nativeJ.opencvJ.libinterface.utils.ClearUtils;
import com.sun.jna.Library;
import com.sun.jna.Native;
import lombok.Getter;

@Getter
public enum Instance {

    /**
     *    4.12.0Debug 版本
     */
    OPENCV_JNA_D(OpenCVConfig.OPENCV_VERSION_4_12_0D, OpencvJNA.class),
    IMAGE_JNA_D(OpenCVConfig.OPENCV_VERSION_4_12_0D, ImageCore.class),
    CLEAR_UTILS_D(OpenCVConfig.OPENCV_VERSION_4_12_0D, ClearUtils.class),
    EDGE_DETECTION_JNA_D(OpenCVConfig.OPENCV_VERSION_4_12_0D, EdgeDetection.class),
    /**
     *
     *    4.12.0 版本
     */
    OPENCV_JNA(OpenCVConfig.OPENCV_VERSION_4_12_0, OpencvJNA.class),
    IMAGE_JNA(OpenCVConfig.OPENCV_VERSION_4_12_0, ImageCore.class);

    private final String path;
    private final Class<? extends Library> nativeClass;
    private Library instance;
    <T extends Library> Instance(OpenCVConfig config, Class<T> nativeClass){
        this.path= config.getLibPath();
        System.out.println("path:"+this.path);
        this.nativeClass=nativeClass;
    }

    @SuppressWarnings("unchecked") // 确保类型安全，抑制警告
    public <T extends Library> T getInstance() {
        if (instance == null) {
            // 延迟到第一次调用时加载
            instance = Native.load(this.path, this.nativeClass);
        }
        return (T) instance;
    }
}
