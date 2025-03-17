package io.jeidiiy.sirenordersystem.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.store.exception.StoreNotFoundException;
import io.jeidiiy.sirenordersystem.store.repository.StoreJpaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[Service] 매장 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
  @InjectMocks StoreService sut;

  @Mock StoreJpaRepository storeJpaRepository;

  private List<Store> stores;

  @BeforeEach
  void setup() {
    stores =
        List.of(
            Store.of("매장0", "주소0", "이미지0", "010-1234-5678", "09:00", "21:00", true),
            Store.of("매장1", "주소1", "이미지1", "010-1234-5678", "09:00", "21:00", true),
            Store.of("매장2", "주소2", "이미지2", "010-1234-5679", "09:00", "21:00", true));
  }

  @DisplayName("매장 목록 전체를 조회한다.")
  @Test
  void givenNothing_whenFinding_thenReturnStores() {
    // given
    given(storeJpaRepository.findAll()).willReturn(stores);

    // when
    sut.findStores();

    // then
    then(storeJpaRepository).should().findAll();
  }

  @DisplayName("특정 매장을 조회한다.")
  @Test
  void givenStoreId_whenFinding_thenReturnsStore() {
    // given
    Integer storeId = 1;
    Store store = stores.get(storeId);
    ReflectionTestUtils.setField(store, "id", storeId);
    given(storeJpaRepository.findById(storeId)).willReturn(Optional.of(store));

    // when
    sut.findStoreById(storeId);

    // then
    then(storeJpaRepository).should().findById(storeId);
  }

  @DisplayName("특정 매장 조회 시 매장이 존재하지 않으면 예외를 발생시킨다.")
  @Test
  void givenNonExistsStoreId_whenFinding_thenThrowsStoreNotFoundException() {
    // given
    Integer nonExistsStoreId = 9999;
    given(storeJpaRepository.findById(nonExistsStoreId)).willReturn(Optional.empty());

    // when
    Throwable t = catchThrowable(() -> sut.findStoreById(nonExistsStoreId));

    // then
    assertThat(t)
        .isInstanceOf(StoreNotFoundException.class)
        .hasMessageContaining("매장을 찾지 못했습니다. storeId: " + nonExistsStoreId);
    then(storeJpaRepository).should().findById(nonExistsStoreId);
  }
}
