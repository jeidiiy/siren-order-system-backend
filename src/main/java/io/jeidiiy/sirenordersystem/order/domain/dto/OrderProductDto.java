package io.jeidiiy.sirenordersystem.order.domain.dto;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderProductDto(
    @NotNull(message = "상품 ID는 필수값입니다") Integer productId,
    @NotNull(message = "상품 개수는 필수값입니다") Integer quantity,
    @NotNull(message = "상품 카테고리는 필수값입니다") Category category,
    @Valid Object options) {}
