package com.erling.serviceJ.user;


import com.erling.pojo.user.PassWord;
import com.erling.daoJ.user.PassWordMapper;
import com.erling.pojo.user.User;
import com.erling.daoJ.user.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceJ {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PassWordMapper passWordMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
    public PassWord getPassWordByUsername(int id) {
        return passWordMapper.selectById(id);
    }




}
