package io.jeidiiy.sirenordersystem.order.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "order_product_options")
@Entity
public class OrderProductOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private OrderProduct orderProduct;

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
    OrderProductOption that = (OrderProductOption) o;

    if (that.getId() != null) {
      return Objects.equals(getId(), that.getId());
    }

    return Objects.equals(getOrderProduct(), that.getOrderProduct())
        && Objects.equals(getBeverageOption(), that.getBeverageOption())
        && Objects.equals(getBeverageOptionValue(), that.getBeverageOptionValue())
        && Objects.equals(getQuantity(), that.getQuantity())
        && Objects.equals(getExtraPrice(), that.getExtraPrice());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(
        getOrderProduct(),
        getBeverageOption(),
        getBeverageOptionValue(),
        getQuantity(),
        getExtraPrice());
  }
}
