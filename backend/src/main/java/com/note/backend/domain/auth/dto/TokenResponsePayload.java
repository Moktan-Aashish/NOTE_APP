package com.note.backend.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenResponsePayload(
        @NotBlank String accessToken,
        @NotBlank String refreshToken
) { }
