package io.jeidiiy.sirenordersystem.order.controller;

import io.jeidiiy.sirenordersystem.order.domain.dto.OrderPostRequestBody;
import io.jeidiiy.sirenordersystem.order.service.OrderService;
import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

  private final OrderService orderService;

  @PreAuthorize("hasRole('CUSTOMER') OR hasAuthority('CUSTOMER')")
  @PostMapping
  public ResponseEntity<Void> createOrder(
      @AuthenticationPrincipal AuthenticationUser currentUser,
      @Valid @RequestBody OrderPostRequestBody requestBody) {
    orderService.createOrder(requestBody, currentUser.getUsername());
    return ResponseEntity.ok().build();
  }
}
