package com.erling.testJ;

import com.erling.controllerJ.example.LoadLibTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTest3 {
    @Test
    public void TEST_1(){
        LoadLibTest loadLibTest=new LoadLibTest();
        int result=loadLibTest.add(1,2);
        System.out.println(result);
        Assertions.assertEquals(3,result);
    }
}
