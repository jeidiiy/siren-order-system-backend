package io.jeidiiy.sirenordersystem.store.domain.dto;

import io.jeidiiy.sirenordersystem.store.domain.PickupOption;

public record PickupOptionResponseDto(String name, String description) {
  public static PickupOptionResponseDto from(PickupOption pickupOption) {
    return new PickupOptionResponseDto(pickupOption.getName(), pickupOption.getDescription());
  }
}
