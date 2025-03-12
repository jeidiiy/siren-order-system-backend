package io.jeidiiy.sirenordersystem.type.domain.dto;

import io.jeidiiy.sirenordersystem.type.domain.Type;

public record TypeResponseDto(String title, String description) {
  public static TypeResponseDto from(Type type) {
    return new TypeResponseDto(type.getTitle(), type.getDescription());
  }
}
