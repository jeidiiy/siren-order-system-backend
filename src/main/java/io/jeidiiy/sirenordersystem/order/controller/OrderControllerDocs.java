package io.jeidiiy.sirenordersystem.order.controller;

import io.jeidiiy.sirenordersystem.order.domain.dto.OrderPostRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.AuthenticationUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "주문 API")
public interface OrderControllerDocs {

  @Operation(summary = "주문하기", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "주문요청"))
  @PreAuthorize("hasRole('CUSTOMER') OR hasAuthority('CUSTOMER')")
  @PostMapping
  public ResponseEntity<Void> createOrder(
      @AuthenticationPrincipal AuthenticationUser currentUser,
      @Valid @RequestBody OrderPostRequestBody requestBody);
}
