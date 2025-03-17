package io.jeidiiy.sirenordersystem.product.domain.beverage.dto;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.dto.ProductDto;

public class BeverageDto extends ProductDto {
  public BeverageDto(
      Integer id,
      String krName,
      String enName,
      String description,
      Integer basePrice,
      String imageUrl) {
    super(id, krName, enName, description, basePrice, imageUrl, Category.BEVERAGE);
  }
}
