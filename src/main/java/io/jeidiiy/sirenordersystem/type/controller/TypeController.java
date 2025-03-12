package io.jeidiiy.sirenordersystem.type.controller;

import io.jeidiiy.sirenordersystem.type.domain.Category;
import io.jeidiiy.sirenordersystem.type.domain.dto.TypeResponseDto;
import io.jeidiiy.sirenordersystem.type.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/types")
@RestController
public class TypeController {

  private final TypeService typeService;

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
