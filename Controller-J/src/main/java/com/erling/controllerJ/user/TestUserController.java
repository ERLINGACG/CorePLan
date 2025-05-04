package com.erling.controllerJ.user;
import com.erling.daoJ.user.PassWord;
import com.erling.serviceJ.user.UserServiceJ;
import com.erling.serviceJ.user.login.Login;
import com.erling.serviceJ.user.login.Register;
import com.erling.utilJ.cryptography.CryptographySHA;
import com.erling.daoJ.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/test3")
    public Map<User, PassWord> test3(@RequestParam String username, @RequestParam String password) {
        boolean result = register.register(username, password);
        Map<User, PassWord> map = new HashMap<>();
        User user = userServiceJ.getUserByUsername(username);
        PassWord passWord = userServiceJ.getPassWordByUsername(user.getId());
        map.put(user, passWord);
        System.out.println("result: " + result);
        return map;
    }
    @RequestMapping(value = "/test4")
    public Boolean test4(@RequestParam String username, @RequestParam String password) {
        boolean result = login.login(username, password);
        System.out.println("result: " + result);
        return result;
    }

}
