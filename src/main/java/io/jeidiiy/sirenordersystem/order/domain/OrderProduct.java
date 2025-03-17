package io.jeidiiy.sirenordersystem.order.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeidiiy.sirenordersystem.product.domain.Product;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.Map;
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

  @Column(columnDefinition = "TEXT")
  private String options;

  // JSON을 Map으로 변환 (getter)
  public Map<String, Object> getOptionsAsMap() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(this.options, new TypeReference<>() {});
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse JSON", e);
    }
  }

  // Map을 JSON 문자열로 변환 (setter)
  public void setOptionsFromMap(Map<String, Object> optionsMap) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      this.options = objectMapper.writeValueAsString(optionsMap);
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert to JSON", e);
    }
  }

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
        && Objects.equals(getQuantity(), orderProduct.getQuantity())
        && Objects.equals(getOptions(), orderProduct.getOptions());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getOrder(), getProduct(), getQuantity(), getOptions());
  }
}
