package io.jeidiiy.sirenordersystem.cart.domain.dto;

import jakarta.validation.constraints.NotNull;

public record CartPostRequestDto(Integer cartId, @NotNull Integer productId, Integer quantity) {}
