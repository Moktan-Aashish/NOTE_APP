package com.note.backend.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final String uri;
    private final Instant timestamp;

    private ApiResponse(boolean success, String message, T data, String uri) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.uri = uri;
        this.timestamp = Instant.now();
    }

    public static <T> ApiResponse<T> success(String message, T data, String uri) {
        return new ApiResponse<>(true, message, data, uri);
    }

    public static <T> ApiResponse<T> success(String message, String uri) {
        return new ApiResponse<>(true, message, null, uri);
    }

    public static <T> ApiResponse<T> error(String message, String uri) {
        return new ApiResponse<>(false, message, null, uri);
    }

    public static <T> ApiResponse<T> error(String message, String uri, T errorDetails) {
        return new ApiResponse<>(false, message, errorDetails, uri);
    }
}