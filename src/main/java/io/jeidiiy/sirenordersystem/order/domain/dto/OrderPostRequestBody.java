package io.jeidiiy.sirenordersystem.order.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderPostRequestBody(
    @NotNull(message = "매장 ID는 필수값입니다") Integer storeId,
    @NotNull(message = "픽업 옵션은 필수값입니다") String pickupOption,
    @NotNull(message = "주문한 상품 정보는 필수값입니다") @Valid List<OrderProductDto> orderProductDtos) {}
