package com.neu.zboyn.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int code;

    @JsonProperty
    private T data;

    @JsonProperty
    private String message;

    @JsonProperty
    private String error;

    // 静态工厂方法：成功
    public static <T> Response<T> success(T data, String message, String error) {
        // 成功的状态码固定为 0
        return new Response<>(0, data, message, error);
    }

    // 重载版本，使用默认成功消息
    public static <T> Response<T> success(T data) {
        // 成功的状态码固定为 0
        return new Response<>(0, data, "操作成功", "ok");
    }

    // 静态工厂方法：失败
    public static <T> Response<T> error(int code, String message, String error) {
        // 失败时，code 应为非零值
        // 调用时传入 400, 401, 500 等
        return new Response<>(code, null, message, error);
    }


}
