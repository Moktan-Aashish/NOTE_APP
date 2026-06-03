package com.note.backend.domain.auth.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.note.backend.core.exception.custom.auth.AuthProviderException;
import com.note.backend.core.exception.custom.auth.InvalidCredentialsExceptions;
import com.note.backend.core.exception.custom.user.UserAlreadyExistsException;
import com.note.backend.core.security.JwtUtils;
import com.note.backend.domain.auth.dto.*;
import com.note.backend.domain.auth.enums.AuthProvider;
import com.note.backend.domain.auth.enums.Token;
import com.note.backend.domain.auth.mapper.AuthDtoMapper;
import com.note.backend.domain.auth.service.AuthService;
import com.note.backend.domain.auth.service.GoogleTokenVerifier;
import com.note.backend.domain.user.model.User;
import com.note.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtils jwtUtils;
    private final AuthDtoMapper dtoMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleTokenVerifier googleTokenVerifier;

    @Override
    public TokenResponsePayload loginUser(LoginPayload loginPayload) {

        User user = userRepository.findByEmail(loginPayload.email())
                .orElseThrow(() -> new InvalidCredentialsExceptions("Invalid credentials"));

        if (user.getProvider() != AuthProvider.LOCAL) {
            throw new AuthProviderException("Please login using Google");
        }

        if (!passwordEncoder.matches(loginPayload.password(), user.getPassword())) {
            throw new InvalidCredentialsExceptions("Invalid credentials");
        }

        UserTokenPayload payload = dtoMapper.toDto(user);

        String accessToken = jwtUtils.generateToken(Token.ACCESS_TOKEN, payload);
        String refreshToken = jwtUtils.generateToken(Token.REFRESH_TOKEN, payload);

        return new TokenResponsePayload(accessToken, refreshToken);
    }

    @Override
    public TokenResponsePayload registerUser(SignupReqPayload signupReqPayload) {

        if (userRepository.existsByEmail(signupReqPayload.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        SignupPayload signupPayload =
                new SignupPayload(signupReqPayload.username(), signupReqPayload.email());

        User newUser = dtoMapper.toEntity(signupPayload, AuthProvider.LOCAL);

        newUser.setPassword(passwordEncoder.encode(signupReqPayload.password()));
        newUser.setProviderId(null);

        User savedUser = userRepository.save(newUser);

        UserTokenPayload payload = dtoMapper.toDto(savedUser);

        String accessToken = jwtUtils.generateToken(Token.ACCESS_TOKEN, payload);
        String refreshToken = jwtUtils.generateToken(Token.REFRESH_TOKEN, payload);

        return new TokenResponsePayload(accessToken, refreshToken);
    }

    @Override
    public TokenResponsePayload googleAuth(GoogleLoginPayload googleLoginPayload) {

        GoogleIdToken.Payload payload =
                googleTokenVerifier.verifyToken(googleLoginPayload.token());

        String email = payload.getEmail();
        String googleId = payload.getSubject();
        String username = (String) payload.get("name");

        User user = userRepository.findByProviderId(googleId).orElse(null);

        if (user == null) {
            user = userRepository.findByEmail(email).orElse(null);

            if (user != null && user.getProvider() == AuthProvider.LOCAL) {
                throw new AuthProviderException("Email already registered with LOCAL account");
            }

            if (user == null) {
                SignupPayload signupPayload = new SignupPayload(username, email);

                user = dtoMapper.toEntity(signupPayload, AuthProvider.GOOGLE);
                user.setProviderId(googleId);
                user.setPassword(null);

                user = userRepository.save(user);
            }
        }

        UserTokenPayload userTokenPayload = dtoMapper.toDto(user);

        String accessToken = jwtUtils.generateToken(Token.ACCESS_TOKEN, userTokenPayload);
        String refreshToken = jwtUtils.generateToken(Token.REFRESH_TOKEN, userTokenPayload);

        return new TokenResponsePayload(accessToken, refreshToken);
    }
}