package com.erling.pojo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "UserEntity",hidden = false)
/* 用户表 */
public class User {
    @Schema(description = "用户ID", example = "123")
    private int id;

    @Schema(description = "登录账号", example = "admin")
    private String username;

    @Schema(description = "管理员标识", example = "true")
    private boolean isAdmin; // 0: 普通用户，1: 管理员，实验阶段暂不考虑

}
