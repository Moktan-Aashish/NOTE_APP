package com.note.backend.core.exception.custom.auth;

public class InvalidCredentialsExceptions extends RuntimeException {
    public InvalidCredentialsExceptions(String message) { super(message); }
    public InvalidCredentialsExceptions(String message, Throwable cause) { super(message, cause); }
}
