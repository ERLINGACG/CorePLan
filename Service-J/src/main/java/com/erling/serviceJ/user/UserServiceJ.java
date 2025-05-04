package com.erling.serviceJ.user;


import com.erling.daoJ.user.PassWordMapper;
import com.erling.daoJ.user.User;
import com.erling.daoJ.user.UserMapper;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceJ {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PassWordMapper passWordMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
    public com.erling.daoJ.user.PassWord getPassWordByUsername(int id) {
        return passWordMapper.selectById(id);
    }




}
