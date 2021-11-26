package com.neukrang.jybot.util;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String error;

    public ApiResponse() {};

    public ApiResponse(boolean isSuccess, T data, String error) {
        this.success = isSuccess;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "");
    }

    public static ApiResponse fail(String errorMessage) {
        return new ApiResponse(false, "", errorMessage);
    }
}
