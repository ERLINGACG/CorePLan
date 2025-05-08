package com.erling.controllerJ.user;


import com.erling.serviceJ.user.UserServiceJ;
import com.erling.serviceJ.user.login.Login;
import com.erling.serviceJ.user.login.Register;
import com.erling.utilJ.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

     private Login login;
     private Register register;

     @Autowired
     public void setLogin(Login login) {
         this.login = login;
     }
     @Autowired
     public void setRegister(Register register) {
         this.register = register;
     }

     @PostMapping("/login")
     public ResponseEntity<Result<?>>login(@RequestParam String username, @RequestParam String password) {
          return login.login(username, password);
     }
     @PostMapping("/register")
     public ResponseEntity<Result<?>> register(@RequestParam String username, @RequestParam String password) {
          return register.register(username, password);
     }

}
