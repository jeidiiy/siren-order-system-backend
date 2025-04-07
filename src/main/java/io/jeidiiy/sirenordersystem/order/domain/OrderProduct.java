package io.jeidiiy.sirenordersystem.order.domain;

import io.jeidiiy.sirenordersystem.product.domain.Product;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
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

  @Column(nullable = false)
  private Integer quantity;

  private OrderProduct(Order order, Product product, Integer quantity) {
    this.order = order;
    this.product = product;
    this.quantity = quantity;
  }

  public static OrderProduct of(Order order, Product product, Integer quantity) {
    return new OrderProduct(order, product, quantity);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderProduct orderProduct = (OrderProduct) o;

    if (orderProduct.getId() != null) {
      return Objects.equals(getId(), orderProduct.getId());
    }

    return Objects.equals(getOrder(), orderProduct.getOrder())
        && Objects.equals(getProduct(), orderProduct.getProduct())
        && Objects.equals(getQuantity(), orderProduct.getQuantity());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getOrder(), getProduct(), getQuantity());
  }
}
