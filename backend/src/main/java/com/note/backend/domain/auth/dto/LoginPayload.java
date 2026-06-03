package com.note.backend.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginPayload(
        @NotBlank String email,
        @NotBlank String password
) { }
