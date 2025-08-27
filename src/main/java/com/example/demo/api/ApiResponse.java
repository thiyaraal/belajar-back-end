package com.example.demo.api;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(String msg, T data) {
        return ApiResponse.<T>builder().success(true).message(msg).data(data).build();
    }
    public static <T> ApiResponse<T> created(String msg, T data) {
        return ok(msg, data);
    }
    public static <T> ApiResponse<T> fail(String msg) {
        return ApiResponse.<T>builder().success(false).message(msg).build();
    }
}