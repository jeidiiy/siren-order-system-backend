package io.jeidiiy.sirenordersystem.product.domain.food.dto;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FoodDto extends ProductDto {
  public FoodDto(
      Integer id,
      String krName,
      String enName,
      String description,
      Integer basePrice,
      String imageUrl) {
    super(id, krName, enName, description, basePrice, imageUrl, Category.BEVERAGE);
  }
}
