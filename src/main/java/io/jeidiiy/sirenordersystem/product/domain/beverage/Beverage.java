package io.jeidiiy.sirenordersystem.product.domain.beverage;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.product.domain.Category;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("BEVERAGE")
public class Beverage extends Product {
  public Beverage(
      String krName, String enName, String description, Integer basePrice, String imageUrl) {
    super(krName, enName, description, basePrice, imageUrl, Category.BEVERAGE);
  }
}
