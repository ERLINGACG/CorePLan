package com.erling.serviceJ.user.login;


import com.erling.daoJ.user.PassWord;
import com.erling.daoJ.user.PassWordMapper;
import com.erling.daoJ.user.User;
import com.erling.daoJ.user.UserMapper;
import com.erling.utilJ.cryptography.CryptographySHA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Login {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PassWordMapper passWordMapper;

    public boolean login(String username, String password) {
       try{
           // 验证用户名和密码
           User user =userMapper.getUserByUsername(username);
           if(user!=null){
               PassWord passWord = passWordMapper.selectById(user.getId());
               String DBPassword = passWord.getPassword();
               String salt = passWord.getSalt();

               String inputPassword = CryptographySHA.sha256(password, salt);
               return inputPassword.equals(DBPassword);
           }else{
               return false;
           }

       }
       catch(Exception e){
           e.printStackTrace();
           return false;
       }
    }


}
