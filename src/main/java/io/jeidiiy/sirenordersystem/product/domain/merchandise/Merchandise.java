package io.jeidiiy.sirenordersystem.product.domain.merchandise;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import io.jeidiiy.sirenordersystem.type.domain.Category;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("MERCHANDISE")
public class Merchandise extends Product {
  public Merchandise(
      String krName, String enName, String description, Integer basePrice, String imageUrl) {
    super(krName, enName, description, basePrice, imageUrl, Category.MERCHANDISE);
  }
}
