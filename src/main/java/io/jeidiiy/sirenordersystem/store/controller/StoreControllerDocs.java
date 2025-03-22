package io.jeidiiy.sirenordersystem.store.controller;

import io.jeidiiy.sirenordersystem.store.domain.dto.StorePutRequestBody;
import io.jeidiiy.sirenordersystem.store.domain.dto.StoreResponseDto;
import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매장 API")
public interface StoreControllerDocs {
  @Operation(summary = "전체 매장 조회")
  ResponseEntity<List<StoreResponseDto>> findStores();

  @Operation(summary = "ID 값으로 특정한 매장 조회")
  ResponseEntity<StoreResponseDto> findStoreById(@PathVariable Integer storeId);

  @Operation(
      summary = "매장 영업 오픈/매장 상태 토글",
      description = "해당 매장 관리자 계정이 해당 API 를 호출하면 영업 상태가 토글처리된다.")
  ResponseEntity<Void> toggleStoreIsOpen(
      @AuthenticationPrincipal AuthenticationUser currentUser, @PathVariable Integer storeId);

  @Operation(summary = "매장 정보 수정", description = "해당 매장 관리자 계정이 오픈 상태를 제외한 나머지 정보를 수정한다.")
  ResponseEntity<Void> updateStore(
      @AuthenticationPrincipal AuthenticationUser currentUser,
      @PathVariable Integer storeId,
      @Valid @RequestBody StorePutRequestBody storePutRequestBody);
}
