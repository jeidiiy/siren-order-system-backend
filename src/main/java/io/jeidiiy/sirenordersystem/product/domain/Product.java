package io.jeidiiy.sirenordersystem.product.domain;

import io.jeidiiy.sirenordersystem.product.domain.beverage.Beverage;
import io.jeidiiy.sirenordersystem.product.domain.food.Food;
import io.jeidiiy.sirenordersystem.product.domain.merchandise.Merchandise;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@DiscriminatorColumn(name = "product_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "products")
@Entity
public abstract class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 50, nullable = false)
  private String krName;

  @Column(length = 50, nullable = false)
  private String enName;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private Integer basePrice;

  @Column(length = 2048)
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;

  protected Product(
      Integer id,
      String krName,
      String enName,
      String description,
      Integer basePrice,
      String imageUrl,
      Category category) {
    this.id = id;
    this.krName = krName;
    this.enName = enName;
    this.description = description;
    this.basePrice = basePrice;
    this.imageUrl = imageUrl;
    this.category = category;
  }

  public static Product of(
      Integer id,
      String krName,
      String enName,
      String description,
      Integer basePrice,
      String imageUrl,
      Category category) {
    return switch (category) {
      case BEVERAGE -> new Beverage(id, krName, enName, description, basePrice, imageUrl);
      case FOOD -> new Food(id, krName, enName, description, basePrice, imageUrl);
      case MERCHANDISE -> new Merchandise(id, krName, enName, description, basePrice, imageUrl);
    };
  }
}
