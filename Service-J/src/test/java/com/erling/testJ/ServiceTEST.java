package com.erling.testJ;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.erling.Application.class)
public class ServiceTEST {
    @Test
    public void test() {
        System.out.println("Hello, world!");
    }
}
