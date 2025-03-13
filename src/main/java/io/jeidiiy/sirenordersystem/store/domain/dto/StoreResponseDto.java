package io.jeidiiy.sirenordersystem.store.domain.dto;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.domain.StorePickupOption;
import lombok.Builder;

import java.util.List;

@Builder
public record StoreResponseDto(
    Integer storeId,
    String storeName,
    String contactNumber,
    String openAt,
    String closeAt,
    List<PickupOptionResponseDto> pickupOptionsResponseDto) {
  public static StoreResponseDto from(Store store) {
    return StoreResponseDto.builder()
        .storeId(store.getId())
        .storeName(store.getName())
        .contactNumber(store.getContactNumber())
        .openAt(store.getOpenAt())
        .closeAt(store.getCloseAt())
        .pickupOptionsResponseDto(
            store.getStorePickupOptions().stream()
                .map(StorePickupOption::getPickupOption)
                .map(PickupOptionResponseDto::from)
                .toList())
        .build();
  }
}
