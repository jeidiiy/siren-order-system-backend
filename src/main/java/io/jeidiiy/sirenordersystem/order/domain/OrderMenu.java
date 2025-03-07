package io.jeidiiy.sirenordersystem.order.domain;

import io.jeidiiy.sirenordersystem.menu.domain.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Table(name = "order_menus")
@Entity
public class OrderMenu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer orderMenuId;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Menu menu;

  @Column private Integer quantity;

  @Column private Integer totalPrice;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderMenu orderMenu = (OrderMenu) o;

    if (orderMenu.getOrderMenuId() != null) {
      return Objects.equals(getOrderMenuId(), orderMenu.getOrderMenuId());
    }

    return Objects.equals(getOrder(), orderMenu.getOrder())
        && Objects.equals(getMenu(), orderMenu.getMenu())
        && Objects.equals(getQuantity(), orderMenu.getQuantity())
        && Objects.equals(getTotalPrice(), orderMenu.getTotalPrice());
  }

  @Override
  public int hashCode() {
    if (getOrderMenuId() != null) {
      return Objects.hash(getOrderMenuId());
    }

    return Objects.hash(getOrder(), getMenu(), getQuantity(), getTotalPrice());
  }
}
