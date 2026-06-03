package com.note.backend.domain.auth.controller;

import com.note.backend.core.response.ApiResponse;
import com.note.backend.domain.auth.dto.GoogleLoginPayload;
import com.note.backend.domain.auth.dto.LoginPayload;
import com.note.backend.domain.auth.dto.SignupReqPayload;
import com.note.backend.domain.auth.dto.TokenResponsePayload;
import com.note.backend.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<TokenResponsePayload> login(@Valid @RequestBody LoginPayload loginPayload, HttpServletRequest request) {
        TokenResponsePayload responsePayload = authService.loginUser(loginPayload);

        return ApiResponse.success("Login successful", responsePayload, request.getRequestURI());
    }

    @PostMapping("/register")
    public ApiResponse<TokenResponsePayload> signUp(@Valid @RequestBody SignupReqPayload signupReqPayload, HttpServletRequest request) {
        TokenResponsePayload responsePayload = authService.registerUser(signupReqPayload);

        return ApiResponse.success("Signup successful", responsePayload, request.getRequestURI());
    }

    @PostMapping("/google")
    public ApiResponse<TokenResponsePayload> googleAuth(@Valid @RequestBody GoogleLoginPayload googleLoginPayload, HttpServletRequest request) {
        TokenResponsePayload responsePayload = authService.googleAuth(googleLoginPayload);

        return ApiResponse.success("Google authentication successful",responsePayload, request.getRequestURI());
    }
}
