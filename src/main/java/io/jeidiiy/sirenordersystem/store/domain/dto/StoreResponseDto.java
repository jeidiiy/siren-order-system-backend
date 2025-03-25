package io.jeidiiy.sirenordersystem.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.domain.StorePickupOption;
import java.util.List;
import lombok.Builder;

@Builder
public record StoreResponseDto(
    Integer storeId,
    String imageUrl,
    String storeName,
    String address,
    String contactNumber,
    String openAt,
    String closeAt,
    @JsonProperty("pickupOptions") List<PickupOptionResponseDto> pickupOptionsResponseDto) {
  public static StoreResponseDto from(Store store) {
    return StoreResponseDto.builder()
        .storeId(store.getId())
        .imageUrl(store.getImageUrl())
        .storeName(store.getName())
        .address(store.getAddress())
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
