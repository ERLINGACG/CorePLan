package com.erling.nativeJ.opencvJni.libinterface.basic;


import java.nio.Buffer;

public class EdgeDetectionJni {



    public  native  byte[] TestCanny(byte[] img);

    public  native  byte[] TestNoCudaCanny(byte[] img);


    public  native Buffer CannyJNI(Buffer img);

}
