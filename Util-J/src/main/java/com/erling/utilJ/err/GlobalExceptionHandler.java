package com.erling.utilJ.err;


import com.erling.utilJ.log.LOGGER;
import com.erling.utilJ.result.Result;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Result<?>> handleSignatureException(SignatureException e) {
        LOGGER.getLogger(GlobalExceptionHandler.class).error("签名异常", e);
        return ResponseEntity.
                status(400).
                body(new Result<>(400, "签名错误,请先登录", e.getMessage()));
    }


    @ExceptionHandler(IOException.class) // 添加专门处理IO异常的注解
    public ResponseEntity<Result<?>> handleIOException(IOException e) {
        LOGGER.getLogger(GlobalExceptionHandler.class).error("IO异常", e);
        return ResponseEntity.status(500)
                .body(new Result<>(500, "文件操作失败", e.getMessage()));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Result<?>> handleMalformedJwtException(MalformedJwtException e) {
        LOGGER.getLogger(GlobalExceptionHandler.class).error("JWT异常", e);
        return ResponseEntity.
                status(400).
                body(new Result<>(400, "JWT错误", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception e) {
        LOGGER.getLogger(GlobalExceptionHandler.class).error("内部异常", e);
        return ResponseEntity.
                status(500).
                body(new Result<>(500, "服务器内部错误", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        LOGGER.getLogger(GlobalExceptionHandler.class).error("参数异常", e);
        return ResponseEntity.
                status(400).
                body(new Result<>(400, "参数错误", e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Result<?>> handleIllegalStateException(IllegalStateException e) {
        LOGGER.getLogger(GlobalExceptionHandler.class).error("状态异常", e);
        return ResponseEntity.
                status(400).
                body(new Result<>(400, "状态错误", e.getMessage()));
    }
}
