package com.erling.testJ;


import com.erling.daoJ.user.User;
import com.erling.daoJ.user.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;
@SpringBootTest(classes = com.erling.Application.class)
@Service
public class UserServiceJTest {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void test(){
        User user =userMapper.getUserById(1);
        String username = user.getUsername();
        System.out.println(username);
    }





}
