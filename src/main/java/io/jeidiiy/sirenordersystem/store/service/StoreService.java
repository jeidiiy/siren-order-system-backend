package io.jeidiiy.sirenordersystem.store.service;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.domain.dto.StorePutRequestBody;
import io.jeidiiy.sirenordersystem.store.domain.dto.StoreResponseDto;
import io.jeidiiy.sirenordersystem.store.exception.StoreNotFoundException;
import io.jeidiiy.sirenordersystem.store.repository.StoreJpaRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService {

  private final StoreJpaRepository storeJpaRepository;

  @Transactional(readOnly = true)
  public List<StoreResponseDto> findStores() {
    return storeJpaRepository.findAllWithPickupOptions().stream()
        .map(StoreResponseDto::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public StoreResponseDto findStoreById(Integer storeId) {
    return StoreResponseDto.from(findById(storeId));
  }

  @Transactional(readOnly = true)
  public Store findById(Integer storeId) {
    return storeJpaRepository
        .findByIdWithPickupOptions(storeId)
        .orElseThrow(() -> new StoreNotFoundException(storeId));
  }

  public void toggleStoreIsOpenByStoreIdAndLoginUserUsername(
      Integer storeId, String currentUserUsername) {
    Store store = findStoreByStoreIdAndUserUsername(storeId, currentUserUsername);
    store.setIsOpen(!store.getIsOpen());
    storeJpaRepository.save(store);
  }

  @Transactional(readOnly = true)
  public Store findStoreByStoreIdAndUserUsername(Integer storeId, String currentUserUsername) {
    return storeJpaRepository
        .findByIdAndUserUsername(storeId, currentUserUsername)
        .orElseThrow(() -> new StoreNotFoundException(storeId, currentUserUsername));
  }

  public void updateStoreByStoreIdAndLoginUserUsername(
      StorePutRequestBody requestBody, Integer storeId, String currentUsername) {
    Store store = findStoreByStoreIdAndUserUsername(storeId, currentUsername);
    store.update(requestBody);
    storeJpaRepository.save(store);
  }
}
