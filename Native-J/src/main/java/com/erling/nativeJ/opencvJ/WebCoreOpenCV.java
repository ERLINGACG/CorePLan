package com.erling.nativeJ.opencvJ;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class WebCoreOpenCV {
//    private static final String path = WebCoreOpenCV.class.getClassLoader()
//            .getResource("native/WebCoreOpenCV.dll").getPath().replaceFirst("/", "");
     private static final String path = "lib\\WebCoreOpenCV.dll";

    public interface  MyClib extends Library{
        MyClib INSTANCE= Native.load(path,MyClib.class);
        Pointer openImage(IntByReference outWidth, IntByReference outHeight, IntByReference outSize);
        Pointer SURFJ(byte[] image,int length, IntByReference outWidth, IntByReference outHeight, IntByReference outSize);

        void releaseImage(Pointer image);
    }

    public static byte[] openImage() {
        IntByReference outWidth = new IntByReference();
        IntByReference outHeight = new IntByReference();
        IntByReference outSize = new IntByReference();
        Pointer pointer = MyClib.INSTANCE.openImage(outWidth, outHeight, outSize);
        System.out.println("图片宽度: " + outWidth.getValue());
        System.out.println("图片高度: " + outHeight.getValue());
        System.out.println("图片大小: " + outSize.getValue());
        return pointer.getByteArray(0, outSize.getValue());
    }
    public static byte[] SURFJ(byte[] imageData) {
//        byte[] imageData = WebCoreOpenCV.openImage();
        Pointer pointer = null;
        try {
            IntByReference outWidth = new IntByReference();
            IntByReference outHeight = new IntByReference();
            IntByReference outSize = new IntByReference();
            pointer = MyClib.INSTANCE.SURFJ(imageData, imageData.length, outWidth, outHeight, outSize);
            return pointer.getByteArray(0, outSize.getValue());
        } finally {
            if (pointer != null) {
                MyClib.INSTANCE.releaseImage(pointer);
            }
        }
    }
}
