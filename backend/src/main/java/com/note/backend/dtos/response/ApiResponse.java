package com.note.backend.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final String uri;
    private final LocalDateTime temeStamp;

    public static <T> ApiResponse<T> success(String message, String uri) {
        return  ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(null)
                .uri(uri)
                .temeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data, String uri) {
        return  ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .uri(uri)
                .temeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String uri) {
        return  ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .uri(uri)
                .temeStamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, T errorDetails, String uri) {
        return  ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(errorDetails)
                .uri(uri)
                .temeStamp(LocalDateTime.now())
                .build();
    }
}
