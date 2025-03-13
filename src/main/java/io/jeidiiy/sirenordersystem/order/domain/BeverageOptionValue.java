package io.jeidiiy.sirenordersystem.order.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "beverage_option_values")
@Entity
public class BeverageOptionValue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

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

    if (that.getId() != null) {
      return Objects.equals(getId(), that.getId());
    }

    return Objects.equals(getBeverageOption(), that.getBeverageOption())
        && Objects.equals(getValue(), that.getValue())
        && Objects.equals(getExtraPrice(), that.getExtraPrice());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getBeverageOption(), getValue(), getExtraPrice());
  }
}
