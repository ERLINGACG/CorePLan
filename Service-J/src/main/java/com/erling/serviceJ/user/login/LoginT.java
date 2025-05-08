package com.erling.serviceJ.user.login;

import com.erling.daoJ.user.PassWordMapper;
import com.erling.daoJ.user.UserMapper;
import com.erling.pojo.user.PassWord;
import com.erling.pojo.user.User;
import com.erling.utilJ.cryptography.CryptographySHA;
import com.erling.utilJ.result.Result;
import com.erling.utilJ.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class LoginT {
//    private static final Logger log = LoggerFactory.getLogger(LoginT.class);
//
//    private UserMapper userMapper;
//    @Autowired
//    public void setUserMapper(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
//
//
//    private PassWordMapper passWordMapper;
//    @Autowired
//    public void setPassWordMapper(PassWordMapper passWordMapper) {
//        this.passWordMapper = passWordMapper;
//    }
//
//    public ResponseEntity<Result<?>> login(String username, String password) {
//        try{
//            // 验证用户名和密码
//            User user =userMapper.getUserByUsername(username);
//            if(user!=null){
//                PassWord passWord = passWordMapper.selectById(user.getId());
//                String DBPassword = passWord.getPassword();
//                String salt = passWord.getSalt();
//
//                String inputPassword = CryptographySHA.sha256(password, salt);
//                if(inputPassword.equals(DBPassword)) return ResponseEntity.
//                        status(HttpStatus.OK).
//                        body(Result.SUCCESS("success"));
//
//                return ResponseEntity.
//                        status(HttpStatus.UNAUTHORIZED).
//                        body(Result.FAILURE(401, "密码或者户名错误", null));
//            }else{
//                return ResponseEntity.
//                        status(HttpStatus.UNAUTHORIZED).
//                        body(Result.FAILURE(401, "用户不存在", null));
//            }
//
//        }
//        catch(Exception e){
//            log.error("登录失败", e);
//            return ResponseEntity.
//                    status(HttpStatus.INTERNAL_SERVER_ERROR).
//                    body(new Result<>(ResultCode.INTERNAL_ERROR,null));
//        }
//    }
}
