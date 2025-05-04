package com.erling.controllerJ.user;


import com.erling.serviceJ.user.UserServiceJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
     @Autowired
     private UserServiceJ userServiceJ;

//    @GetMapping("/TestPassword")
//     public Map<String, String> TestPassword(){
//         return userServiceJ.TestPassword("123456");
//     }


}
