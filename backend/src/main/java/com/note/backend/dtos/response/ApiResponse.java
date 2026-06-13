package com.note.backend.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response for the API")
public class ApiResponse<T> {

    @Schema(
            description = "Indicates the success of the request",
            example = "true"
    )
    private final boolean success;

    @Schema(
            description = "Readable response message",
            example = "Note fetched successfully"
    )
    private final String message;

    @Schema(description = "Response payload")
    private final T data;

    @Schema(
            description = "URI of the request",
            example = "/api/notes"
    )
    private final String uri;

    @Schema(
            description = "Response timestamp",
            example = "2026-06-13T17:30:00"
    )
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
