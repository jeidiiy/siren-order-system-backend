package io.jeidiiy.sirenordersystem.product.controller;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.dto.TypeResponseDto;
import io.jeidiiy.sirenordersystem.product.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "종류 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/types")
@RestController
public class TypeController {

  private final TypeService typeService;

  @Operation(summary = "카테고리에 해당하는 종류 전체 조회")
  @GetMapping("/{categoryName}")
  public ResponseEntity<List<TypeResponseDto>> findAllByCategory(
      @PathVariable String categoryName) {
    try {
      return ResponseEntity.ok(
          typeService.findTypeByCategory(Category.valueOf(categoryName.toUpperCase())));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("해당 카테고리는 존재하지 않습니다. - " + categoryName);
    }
  }
}
