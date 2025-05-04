package com.erling.serviceJ.user.login;

import com.erling.daoJ.user.PassWordMapper;
import com.erling.daoJ.user.User;
import com.erling.daoJ.user.UserMapper;
import com.erling.utilJ.cryptography.CryptographySHA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Register {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PassWordMapper passWordMapper;

    public boolean register(String username, String password) {
        try{
            User user = userMapper.getUserByUsername(username);
            if(user==null){
                userMapper.addUser(username);
                int id = userMapper.getID(username);
                String salt = CryptographySHA.generateSalt(16);
                String NewPassword = CryptographySHA.sha256(password, salt);
                passWordMapper.addPassWord(id, NewPassword, salt);
            }else{
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
