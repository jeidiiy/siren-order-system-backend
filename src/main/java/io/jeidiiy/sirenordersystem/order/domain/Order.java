package io.jeidiiy.sirenordersystem.order.domain;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.user.domain.User;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "orders")
@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Store store;

  @Column private Integer totalPrice;

  @Enumerated(EnumType.STRING)
  @Column
  private OrderStatus orderStatus;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;

    if (order.getId() != null) {
      return Objects.equals(getId(), order.getId());
    }

    return Objects.equals(getUser(), order.getUser())
        && Objects.equals(getStore(), order.getStore())
        && Objects.equals(getTotalPrice(), order.getTotalPrice())
        && getOrderStatus() == order.getOrderStatus();
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getUser(), getStore(), getTotalPrice(), getOrderStatus());
  }
}
