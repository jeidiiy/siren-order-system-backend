package io.jeidiiy.sirenordersystem.product.controller;

import io.jeidiiy.sirenordersystem.product.domain.dto.TypeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "종류 API")
public interface TypeControllerDocs {
  @Operation(summary = "카테고리에 해당하는 종류 전체 조회")
  ResponseEntity<List<TypeResponseDto>> findAllByCategory(@PathVariable String categoryName);
}
