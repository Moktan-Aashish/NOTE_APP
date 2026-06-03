package com.note.backend.domain.auth.dto;

import com.note.backend.domain.auth.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record UserTokenPayload(
        @NotBlank String userId,
        @NotBlank String email,
        @NotBlank Role role
) {}
