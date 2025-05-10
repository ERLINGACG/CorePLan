package com.erling.nativeJ.opencvJni.implementation;

import com.erling.nativeJ.opencvJni.config.OpenCVJNIConfig;
import com.erling.nativeJ.opencvJni.libinterface.basic.EdgeDetectionJni;
import lombok.Getter;

@Getter
public enum InstanceJNI {
    TEST_INSTANCE(EdgeDetectionJni.class,OpenCVJNIConfig.OPENCV_VERSION_4_12_0);


    private final Class<?> nativeClass;
    private final String libPath;
    <T> InstanceJNI(Class<T> clazz,OpenCVJNIConfig config) {
        this.libPath = config.getLibPath();
        this.nativeClass = clazz;
    }
    @SuppressWarnings("unchecked")
    public  <T> T getInstance() {
        try {
            System.load("E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\lib\\opencv_world4120.dll");
            System.load(libPath);
            return (T) this.nativeClass.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            System.out.println("加载失败"+e.getMessage());
            return null;
        }

    }

}
