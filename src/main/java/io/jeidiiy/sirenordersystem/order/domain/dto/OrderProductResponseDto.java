package io.jeidiiy.sirenordersystem.order.domain.dto;

import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;

public record OrderProductResponseDto(ProductResponseDto productResponseDto, Integer quantity) {
  public static OrderProductResponseDto from(OrderProduct orderProduct) {
    return new OrderProductResponseDto(
        ProductResponseDto.from(orderProduct.getProduct()), orderProduct.getQuantity());
  }
}
