package io.jeidiiy.sirenordersystem.product.domain.dto;

import io.jeidiiy.sirenordersystem.product.domain.Product;

public record ProductResponseDto(
    Integer id, String krName, String enName, Integer basePrice, String imageUrl) {
  public static ProductResponseDto from(Product product) {
    return new ProductResponseDto(
        product.getId(),
        product.getKrName(),
        product.getEnName(),
        product.getBasePrice(),
        product.getImageUrl());
  }
}
