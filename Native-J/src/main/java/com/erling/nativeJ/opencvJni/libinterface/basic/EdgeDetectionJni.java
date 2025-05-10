package com.erling.nativeJ.opencvJni.libinterface.basic;


public class EdgeDetectionJni {

    public  native int ADD(int a, int b);

    public  native  byte[] TestCanny(byte[] img);

    public  native  byte[] TestNoCudaCanny(byte[] img);

}
