package io.jeidiiy.sirenordersystem.type.domain;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "type_products")
@Entity
public class TypeProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer typeProductId;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Type type;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Product product;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TypeProduct typeProduct = (TypeProduct) o;

    if (typeProduct.getTypeProductId() != null) {
      return Objects.equals(getTypeProductId(), typeProduct.getTypeProductId());
    }

    return Objects.equals(getType(), typeProduct.getType())
        && Objects.equals(getProduct(), typeProduct.getProduct());
  }

  @Override
  public int hashCode() {
    if (getTypeProductId() != null) {
      return Objects.hash(getTypeProductId());
    }

    return Objects.hash(getType(), getProduct());
  }
}
