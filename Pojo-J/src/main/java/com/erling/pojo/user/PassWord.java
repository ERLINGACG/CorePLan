package com.erling.pojo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/* 密码表 */
@Builder
@Value
@Data
@Schema(description = "密码")
public class PassWord {

   @Schema(description = "主键")
   int id;

   @Schema(description = "哈希值")
   String password;

   @Schema(description = "盐值")
   String salt;
}
