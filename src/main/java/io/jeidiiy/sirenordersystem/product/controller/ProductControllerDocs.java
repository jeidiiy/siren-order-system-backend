package io.jeidiiy.sirenordersystem.product.controller;

import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "상품 API")
public interface ProductControllerDocs {
  @Operation(summary = "특정 Type 에 해당하는 모든 상품 조회")
  ResponseEntity<List<ProductResponseDto>> getProductByType(@RequestParam Integer typeId);
}
