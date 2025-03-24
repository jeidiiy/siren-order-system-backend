package io.jeidiiy.sirenordersystem.product.domain.dto;

import io.jeidiiy.sirenordersystem.product.domain.Type;

public record TypeResponseDto(Integer id, String title, String description) {
  public static TypeResponseDto from(Type type) {
    return new TypeResponseDto(type.getId(), type.getTitle(), type.getDescription());
  }
}
