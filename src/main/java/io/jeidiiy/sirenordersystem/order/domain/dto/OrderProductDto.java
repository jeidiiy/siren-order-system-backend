package io.jeidiiy.sirenordersystem.order.domain.dto;

import jakarta.validation.constraints.NotNull;

public record OrderProductDto(
    @NotNull(message = "상품 ID는 필수값입니다") Integer id,
    @NotNull(message = "상품 개수는 필수값입니다") Integer quantity
) {}
