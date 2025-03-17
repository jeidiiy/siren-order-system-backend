package io.jeidiiy.sirenordersystem.order.domain;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import io.jeidiiy.sirenordersystem.user.domain.User;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
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

  @Setter @Column private Integer totalPrice;

  @Enumerated(EnumType.STRING)
  @Column
  private OrderStatus orderStatus;

  private Order(User user, Store store, OrderStatus orderStatus) {
    this.user = user;
    this.store = store;
    this.orderStatus = orderStatus;
  }

  public static Order of(User user, Store store, OrderStatus orderStatus) {
    return new Order(user, store, orderStatus);
  }

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
