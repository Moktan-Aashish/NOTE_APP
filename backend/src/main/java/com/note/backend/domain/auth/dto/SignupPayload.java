package com.note.backend.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupPayload(
        @NotBlank String username,
        @NotBlank String email
) { }
