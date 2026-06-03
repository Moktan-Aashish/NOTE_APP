package com.note.backend.core.exception.handlers.auth;

import com.note.backend.core.exception.custom.auth.*;
import com.note.backend.core.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiResponse<Object>> handleTokenExpired(
            TokenExpiredException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        "Authentication token has expired",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ApiResponse<Object>> handleTokenInvalid(
            TokenInvalidException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        "Authentication token is invalid",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(TokenMissingException.class)
    public ResponseEntity<ApiResponse<Object>> handleTokenMissing(
            TokenMissingException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        "Authentication token is missing",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(InvalidCredentialsExceptions.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(
            InvalidCredentialsExceptions exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        "Invalid credentials",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(AuthProviderException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthProvider(
            AuthProviderException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(SecretKeyInitializationException.class)
    public ResponseEntity<ApiResponse<Object>> handleSecretKeyInitialization(
            SecretKeyInitializationException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        "Failed to initialize secret key",
                        request.getRequestURI()
                ));
    }
}