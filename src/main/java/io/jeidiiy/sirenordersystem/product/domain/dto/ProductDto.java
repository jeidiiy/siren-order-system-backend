package io.jeidiiy.sirenordersystem.product.domain.dto;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.beverage.dto.BeverageDto;
import io.jeidiiy.sirenordersystem.product.domain.food.dto.FoodDto;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.dto.MerchandiseDto;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ProductDto {
  private Integer id;

  private String krName;

  private String enName;

  private String description;

  private Integer basePrice;

  private String imageUrl;

  private Category category;

  public static ProductDto from(Product product) {
    return switch (product.getCategory()) {
      case BEVERAGE ->
          new BeverageDto(
              product.getId(),
              product.getKrName(),
              product.getEnName(),
              product.getDescription(),
              product.getBasePrice(),
              product.getImageUrl());
      case FOOD ->
          new FoodDto(
              product.getId(),
              product.getKrName(),
              product.getEnName(),
              product.getDescription(),
              product.getBasePrice(),
              product.getImageUrl());
      case MERCHANDISE ->
          new MerchandiseDto(
              product.getId(),
              product.getKrName(),
              product.getEnName(),
              product.getDescription(),
              product.getBasePrice(),
              product.getImageUrl());
    };
  }

  public static ProductDto of(
      Integer id,
      String krName,
      String enName,
      String description,
      Integer basePrice,
      String imageUrl,
      Category category) {
    return switch (category) {
      case BEVERAGE -> new BeverageDto(id, krName, enName, description, basePrice, imageUrl);
      case FOOD -> new FoodDto(id, krName, enName, description, basePrice, imageUrl);
      case MERCHANDISE -> new MerchandiseDto(id, krName, enName, description, basePrice, imageUrl);
    };
  }

  public Product toEntity() {
    return Product.of(
        getId(),
        getKrName(),
        getEnName(),
        getDescription(),
        getBasePrice(),
        getImageUrl(),
        getCategory());
  }
}
