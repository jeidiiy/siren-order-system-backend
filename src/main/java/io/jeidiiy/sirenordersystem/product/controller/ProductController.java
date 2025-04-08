package io.jeidiiy.sirenordersystem.product.controller;

import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductController {
  private final ProductService productService;

  @Operation(summary = "특정 Type 에 해당하는 모든 상품 조회")
  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> getProductByType(@RequestParam Integer typeId) {
    return ResponseEntity.ok(productService.findAllByTypeId(typeId));
  }
}
