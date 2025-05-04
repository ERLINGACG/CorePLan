package com.erling.daoJ.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
/* 密码表 */
public class PassWord {
   private int id;
   private String password;
   private String salt;
}
