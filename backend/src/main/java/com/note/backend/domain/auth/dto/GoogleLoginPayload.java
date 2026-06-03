package com.note.backend.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record GoogleLoginPayload (
      @NotBlank String token
) {}
