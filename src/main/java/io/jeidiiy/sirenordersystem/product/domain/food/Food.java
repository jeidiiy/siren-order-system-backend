package io.jeidiiy.sirenordersystem.product.domain.food;

import io.jeidiiy.sirenordersystem.product.domain.Category;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Product {
  public Food(
      Integer id,
      String krName,
      String enName,
      String description,
      Integer basePrice,
      String imageUrl) {
    super(id, krName, enName, description, basePrice, imageUrl, Category.FOOD);
  }
}
