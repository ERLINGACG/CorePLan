
package com.erling.testJ;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.erling.Application.class)
public class UserControllerTest {

    @Test
    public void Test1(){
        String expected = "test";
        String actual = "test";
        Assert.assertEquals(expected, actual);
    }
}
