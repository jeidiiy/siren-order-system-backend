package io.jeidiiy.sirenordersystem.store.controller;

import io.jeidiiy.sirenordersystem.store.domain.dto.StorePutRequestBody;
import io.jeidiiy.sirenordersystem.store.domain.dto.StoreResponseDto;
import io.jeidiiy.sirenordersystem.store.service.StoreService;
import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
@RestController
public class StoreController {

  private final StoreService storeService;

  @GetMapping
  public ResponseEntity<List<StoreResponseDto>> findStores() {
    return ResponseEntity.ok(storeService.findStores());
  }

  @GetMapping("/{storeId}")
  public ResponseEntity<StoreResponseDto> findStoreById(@PathVariable Integer storeId) {
    return ResponseEntity.ok(storeService.findStoreById(storeId));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasAuthority('ADMIN')")
  @PatchMapping("/{storeId}")
  public ResponseEntity<Void> toggleStoreIsOpen(
      @AuthenticationPrincipal AuthenticationUser currentUser, @PathVariable Integer storeId) {
    storeService.toggleStoreIsOpenByStoreIdAndLoginUserUsername(storeId, currentUser.getUsername());
    return ResponseEntity.ok().build();
  }

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
