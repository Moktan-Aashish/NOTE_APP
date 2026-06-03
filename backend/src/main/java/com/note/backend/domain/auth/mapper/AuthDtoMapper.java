package com.note.backend.domain.auth.mapper;

import com.note.backend.domain.auth.dto.SignupPayload;
import com.note.backend.domain.auth.dto.UserTokenPayload;
import com.note.backend.domain.auth.enums.AuthProvider;
import com.note.backend.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthDtoMapper {
    public User toEntity(SignupPayload signupPayload, AuthProvider authProvider) {
        if (signupPayload == null) return null;

        User newUser = new User();
        newUser.setUsername(signupPayload.username());
        newUser.setEmail(signupPayload.email());
        newUser.setProvider(authProvider);

        return newUser;
    }

    public UserTokenPayload toDto(User user) {
        return new UserTokenPayload(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
