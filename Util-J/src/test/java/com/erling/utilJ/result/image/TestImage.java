package com.erling.utilJ.result.image;

import com.erling.utilJ.image.ImageData;
import com.sun.jna.ptr.IntByReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestImage {
    @Test
    public void TestImage_1(){
        IntByReference outWidth = new IntByReference();
        IntByReference outHeight = new IntByReference();
        IntByReference outSize = new IntByReference();
        outWidth.setValue(100);
        outHeight.setValue(100);
        outSize.setValue(100);
        ImageData<?> imageData = new ImageData<Integer>(outWidth.getValue(), outHeight.getValue(), outSize.getValue());
        Assertions.assertEquals(100, imageData.getOutWidth(), "图像宽度应该为 100");
    }
}
