package com.erling.controllerJ.example;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
public class LoadLibTest {
    private static final String path="E:\\CorePLAN\\Web\\WebCore\\C\\WebCore-G\\Controller-J\\src\\main\\java\\com\\erling\\controllerJ\\example\\MyProject.dll";
//    static {
//        NativeLibrary.getInstance(path);
//    }
    public interface  MyClib extends Library {
        MyClib INSTANCE=Native.load(path,MyClib.class);
        int add(int a,int b);
    }
    public int add(int a,int b){
        return MyClib.INSTANCE.add(a,b);
    }

}
