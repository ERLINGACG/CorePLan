package com.erling.utilJ.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "通用返回结果")
public class Result<T> {
    private int code;
    private String message;
    private T data;
    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }
    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
    public static <T> Result<T> SUCCESS(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }
    public static <T> Result<T> FAILURE(int code, String message, T data) {
        return new Result<>(code, message, data);
    }


}
