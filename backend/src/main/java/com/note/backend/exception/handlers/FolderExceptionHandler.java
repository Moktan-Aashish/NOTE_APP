package com.note.backend.exception.handlers;

import com.mongodb.DuplicateKeyException;
import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.exception.customs.FolderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FolderExceptionHandler {

    @ExceptionHandler(FolderNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleFolderNotFoundException(
            FolderNotFoundException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("Folder does not exist", request.getRequestURI())
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateKeyException(
            DuplicateKeyException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.error("Folder already exists", request.getRequestURI())
        );
    }
}
