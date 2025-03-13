package io.jeidiiy.sirenordersystem.product.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "product_types")
@Entity
public class ProductType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer productTypeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Type type;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ProductType productType = (ProductType) o;

    if (productType.getProductTypeId() != null) {
      return Objects.equals(getProductTypeId(), productType.getProductTypeId());
    }

    return Objects.equals(getType(), productType.getType())
        && Objects.equals(getProduct(), productType.getProduct());
  }

  @Override
  public int hashCode() {
    if (getProductTypeId() != null) {
      return Objects.hash(getProductTypeId());
    }

    return Objects.hash(getType(), getProduct());
  }
}
