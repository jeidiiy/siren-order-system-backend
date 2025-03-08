package io.jeidiiy.sirenordersystem.user.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestBody(@NotEmpty String username, @NotEmpty String password) {}
