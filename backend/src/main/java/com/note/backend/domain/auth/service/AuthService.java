package com.note.backend.domain.auth.service;


import com.note.backend.domain.auth.dto.GoogleLoginPayload;
import com.note.backend.domain.auth.dto.LoginPayload;
import com.note.backend.domain.auth.dto.SignupReqPayload;
import com.note.backend.domain.auth.dto.TokenResponsePayload;

public interface AuthService {
    TokenResponsePayload loginUser(LoginPayload loginPayload);

    TokenResponsePayload registerUser(SignupReqPayload signupReqPayload);

    TokenResponsePayload googleAuth(GoogleLoginPayload googleLoginPayload);
}
