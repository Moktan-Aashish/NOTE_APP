package com.note.backend.exception.handlers;

import com.note.backend.dtos.response.ApiResponse;
import com.note.backend.exception.customs.NoteNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoteExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoteNotFoundException(
            NoteNotFoundException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("Note does not exist", request.getRequestURI())
        );
    }
}
