package io.jeidiiy.sirenordersystem.cart.domain.dto;

import io.jeidiiy.sirenordersystem.cart.domain.Cart;
import io.jeidiiy.sirenordersystem.product.domain.Product;

public record CartResponseDto(
    Integer cartId, Integer quantity, Integer price, String productKrName) {
  public static CartResponseDto fromEntity(Cart cart) {
    Product product = cart.getProduct();
    return new CartResponseDto(
        cart.getId(), cart.getQuantity(), cart.getPrice(), product.getKrName());
  }
}
