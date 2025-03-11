package io.jeidiiy.sirenordersystem.store.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record StorePutRequestBody(
    @NotNull String name,
    @NotNull String address,
    @NotNull String imageUrl,
    @NotNull String contactNumber,
    @NotNull String openAt,
    @NotNull String closeAt,
    @NotNull Boolean isOpen) {}
