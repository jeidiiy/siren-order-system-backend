package io.jeidiiy.sirenordersystem.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Table(name = "beverage_option_values")
@Entity
public class BeverageOptionValue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer orderMenuOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private BeverageOption beverageOption;

  @Column(length = 30, nullable = false)
  private String value;

  @Column private Integer extraPrice;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BeverageOptionValue that = (BeverageOptionValue) o;

    if (that.getOrderMenuOptionId() != null) {
      return Objects.equals(getOrderMenuOptionId(), that.getOrderMenuOptionId());
    }

    return Objects.equals(getBeverageOption(), that.getBeverageOption())
        && Objects.equals(getValue(), that.getValue())
        && Objects.equals(getExtraPrice(), that.getExtraPrice());
  }

  @Override
  public int hashCode() {
    if (getOrderMenuOptionId() != null) {
      return Objects.hash(getOrderMenuOptionId());
    }

    return Objects.hash(getBeverageOption(), getValue(), getExtraPrice());
  }
}
