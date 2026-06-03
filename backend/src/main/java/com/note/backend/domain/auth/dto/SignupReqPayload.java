package com.note.backend.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupReqPayload(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password
) { }
