package com.erling.test;

import com.erling.daoJ.user.UserMapper;
import com.erling.pojo.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.erling.Application.class)
public class UserMapperTest {

    private UserMapper userMapper;


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }



    @Test
    public void selectByID() {
        User user = userMapper.getUserById(1);
        System.out.println(user);
        Assertions.assertNotNull(user);
    }

}
