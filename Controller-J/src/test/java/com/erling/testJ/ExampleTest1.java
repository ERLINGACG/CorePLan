package com.erling.testJ;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.erling.Application.class)
public class ExampleTest1 {
    @Test
    public void Test2(){
        String expected = "test";
        String actual = "test";
        assert expected.equals(actual);}

}
