package io.jeidiiy.sirenordersystem.order.domain.dto;

import io.jeidiiy.sirenordersystem.order.domain.Order;
import io.jeidiiy.sirenordersystem.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Integer orderId,
        String storeName,
        String storeImageUrl,
        LocalDateTime orderedDateTime,
        OrderStatus orderStatus,
        List<String> orderProductNames,
        Integer totalPrice
) {
    public static OrderResponseDto from(Order order, List<String> orderProductNames) {
        return new OrderResponseDto(
                order.getId(),
                order.getStore().getName(),
                order.getStore().getImageUrl(),
                order.getCreatedAt(),
                order.getOrderStatus(),
                orderProductNames,
                order.getTotalPrice()
        );
    }
}
