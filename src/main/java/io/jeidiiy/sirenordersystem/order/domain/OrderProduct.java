package io.jeidiiy.sirenordersystem.order.domain;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "order_products")
@Entity
public class OrderProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Product product;

  @Column private Integer quantity;

  @Column private Integer totalPrice;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderProduct orderProduct = (OrderProduct) o;

    if (orderProduct.getId() != null) {
      return Objects.equals(getId(), orderProduct.getId());
    }

    return Objects.equals(getOrder(), orderProduct.getOrder())
        && Objects.equals(getProduct(), orderProduct.getProduct())
        && Objects.equals(getQuantity(), orderProduct.getQuantity())
        && Objects.equals(getTotalPrice(), orderProduct.getTotalPrice());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getOrder(), getProduct(), getQuantity(), getTotalPrice());
  }
}
