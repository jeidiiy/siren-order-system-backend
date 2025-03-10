package io.jeidiiy.sirenordersystem.order.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "order_menu_options")
@Entity
public class OrderMenuOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer orderMenuOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private OrderMenu orderMenu;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private BeverageOption beverageOption;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private BeverageOptionValue beverageOptionValue;

  @Column private Integer quantity;

  @Column private Integer extraPrice;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderMenuOption that = (OrderMenuOption) o;

    if (that.getOrderMenuOptionId() != null) {
      return Objects.equals(getOrderMenuOptionId(), that.getOrderMenuOptionId());
    }

    return Objects.equals(getOrderMenu(), that.getOrderMenu())
        && Objects.equals(getBeverageOption(), that.getBeverageOption())
        && Objects.equals(getBeverageOptionValue(), that.getBeverageOptionValue())
        && Objects.equals(getQuantity(), that.getQuantity())
        && Objects.equals(getExtraPrice(), that.getExtraPrice());
  }

  @Override
  public int hashCode() {
    if (getOrderMenuOptionId() != null) {
      return Objects.hash(getOrderMenuOptionId());
    }

    return Objects.hash(
        getOrderMenu(),
        getBeverageOption(),
        getBeverageOptionValue(),
        getQuantity(),
        getExtraPrice());
  }
}
