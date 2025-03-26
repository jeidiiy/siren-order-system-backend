package io.jeidiiy.sirenordersystem.store.controller;

import io.jeidiiy.sirenordersystem.store.domain.dto.StorePutRequestBody;
import io.jeidiiy.sirenordersystem.store.domain.dto.StoreResponseDto;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매장 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
@RestController
public class StoreController {

  private final StoreService storeService;

  @Operation(summary = "전체 매장 조회")
  @GetMapping
  public ResponseEntity<List<StoreResponseDto>> findStores() {
    return ResponseEntity.ok(storeService.findStores());
  }

  @Operation(summary = "ID 값으로 특정한 매장 조회")
  @GetMapping("/{storeId}")
  public ResponseEntity<StoreResponseDto> findStoreById(@PathVariable Integer storeId) {
    return ResponseEntity.ok(storeService.findStoreById(storeId));
  }

  @Operation(
      summary = "매장 영업 오픈/매장 상태 토글",
      description = "해당 매장 관리자 계정이 해당 API 를 호출하면 영업 상태가 토글처리된다.")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasAuthority('ADMIN')")
  @PatchMapping("/{storeId}")
  public ResponseEntity<Void> toggleStoreIsOpen(
      @AuthenticationPrincipal AuthenticationUser currentUser, @PathVariable Integer storeId) {
    storeService.toggleStoreIsOpenByStoreIdAndLoginUserUsername(storeId, currentUser.getUsername());
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "매장 정보 수정", description = "해당 매장 관리자 계정이 오픈 상태를 제외한 나머지 정보를 수정한다.")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasAuthority('ADMIN')")
  @PutMapping("/{storeId}")
  public ResponseEntity<Void> updateStore(
      @AuthenticationPrincipal AuthenticationUser currentUser,
      @PathVariable Integer storeId,
      @Valid @RequestBody StorePutRequestBody storePutRequestBody) {
    storeService.updateStoreByStoreIdAndLoginUserUsername(
        storePutRequestBody, storeId, currentUser.getUsername());
    return ResponseEntity.ok().build();
  }
}
