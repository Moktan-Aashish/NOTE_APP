package com.note.backend.core.exception.custom.auth;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) { super(message); }
    public TokenExpiredException(String message , Throwable cause) { super(message, cause); }
}
