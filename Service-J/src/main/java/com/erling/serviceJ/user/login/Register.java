package com.erling.serviceJ.user.login;

import com.erling.daoJ.user.PassWordMapper;
import com.erling.pojo.user.User;
import com.erling.daoJ.user.UserMapper;
import com.erling.utilJ.cryptography.CryptographySHA;
import com.erling.utilJ.log.LOGGER;
import com.erling.utilJ.result.Result;
import com.erling.utilJ.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Register {

    private UserMapper userMapper;
    private PassWordMapper passWordMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    public void setPassWordMapper(PassWordMapper passWordMapper) {
        this.passWordMapper = passWordMapper;
    }

    public ResponseEntity<Result<?>> register(String username, String password) {
        try{
            User user = userMapper.getUserByUsername(username);
            if(user!=null){
                return ResponseEntity.
                        status(HttpStatus.OK).
                        body(new Result<>(400, "用户已存在", false));

            }else{
                userMapper.addUser(username);
                int id = userMapper.getID(username);
                String salt = CryptographySHA.generateSalt(16);
                String NewPassword = CryptographySHA.sha256(password, salt);
                passWordMapper.addPassWord(id, NewPassword, salt);
                return ResponseEntity.
                        status(HttpStatus.OK).
                        body(new Result<>(200, "注册成功", true));
            }

        } catch (Exception e) {
            LOGGER.getLogger(Register.class).error("注册失败", e);
            return ResponseEntity.
                    status(HttpStatus.OK).
                    body(new Result<>(ResultCode.INTERNAL_ERROR));
        }
    }

}
