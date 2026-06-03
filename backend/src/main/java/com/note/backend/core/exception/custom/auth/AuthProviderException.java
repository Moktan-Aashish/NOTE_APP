package com.note.backend.core.exception.custom.auth;

public class AuthProviderException extends RuntimeException {
    public AuthProviderException(String message) { super(message); }

    public AuthProviderException(String message, Throwable cause) { super(message, cause); }
}
