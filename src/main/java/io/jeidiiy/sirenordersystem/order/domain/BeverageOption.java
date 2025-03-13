package io.jeidiiy.sirenordersystem.order.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "beverage_options")
@Entity
public class BeverageOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 30, nullable = false)
  private String name;

  private Type type;

  @Column(length = 30)
  private String defaultValue;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BeverageOption that = (BeverageOption) o;

    if (that.getId() != null) {
      return Objects.equals(getId(), that.getId());
    }
    return Objects.equals(getName(), that.getName())
        && getType() == that.getType()
        && Objects.equals(getDefaultValue(), that.getDefaultValue());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getName(), getType(), getDefaultValue());
  }
}
