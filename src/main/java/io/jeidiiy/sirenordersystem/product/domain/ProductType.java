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
  private Integer id;

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

    if (productType.getId() != null) {
      return Objects.equals(getId(), productType.getId());
    }

    return Objects.equals(getType(), productType.getType())
        && Objects.equals(getProduct(), productType.getProduct());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getType(), getProduct());
  }
}
