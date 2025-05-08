package com.erling.utilJ.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(HttpStatus.OK.value(), "操作成功"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "请求参数错误"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "资源未找到"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误"),
    USER_NOT_EXIST(HttpStatus.UNAUTHORIZED.value(), "<UNK>" ),;

    private final int code;
    private final String message;


}
