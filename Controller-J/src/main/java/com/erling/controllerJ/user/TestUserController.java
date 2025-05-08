package com.erling.controllerJ.user;
import com.erling.pojo.user.PassWord;
import com.erling.serviceJ.user.UserServiceJ;
import com.erling.serviceJ.user.login.Login;
import com.erling.serviceJ.user.login.LoginT;
import com.erling.serviceJ.user.login.Register;
import com.erling.pojo.user.User;
import com.erling.utilJ.result.Result;
import com.erling.utilJ.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userTest")
public class TestUserController {

    @Autowired
    private Register register;
    @Autowired
    private Login login;

    @Autowired
    private UserServiceJ userServiceJ;

    @Autowired
    private LoginT loginT;

    @RequestMapping(value = "/test1")
    public ResponseEntity<Object> test() {
        System.out.println("test for request mapping");
        Map<String,Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "请求处理完成");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }


//   @RequestMapping(value = "/test2")
//    public User test2(@RequestBody User user) {
//        String password = user.getPassword();
//        String salt = "123456";
//        try{
//            user.setPassword(CryptographySHA.sha256(password, salt));
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return  user;
//   }



    @RequestMapping(value = "/test5")
    public  ResponseEntity<Result<?>> test5(@RequestParam String username) {
        User user = userServiceJ.getUserByUsername(username);

        if (user == null) {

            return ResponseEntity
                    .status(ResultCode.NOT_FOUND.getCode())  // 404状态码
                    .body(new Result<>(ResultCode.NOT_FOUND));
        }else{
            return ResponseEntity
                    .status(ResultCode.SUCCESS.getCode())  // 200状态码
                    .body(new Result<>(ResultCode.SUCCESS,user));
        }

    }
//    @PostMapping("/user")
//    public ResponseEntity<Result<?>> loginT(@RequestParam String username, @RequestParam String password) {
//            return loginT.login(username, password);
//    }
//    @GetMapping("/user")
//    public User getUser() {  // 直接返回User对象
//        return new User();
//    }


}
