package io.jeidiiy.sirenordersystem.product.controller;

import io.jeidiiy.sirenordersystem.product.domain.dto.ProductResponseDto;
import io.jeidiiy.sirenordersystem.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> getProductByType(@RequestParam Integer typeId) {
    return ResponseEntity.ok(productService.findAllByTypeId(typeId));
  }
}
