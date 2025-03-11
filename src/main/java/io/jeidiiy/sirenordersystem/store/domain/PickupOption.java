package io.jeidiiy.sirenordersystem.store.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "pickup_options")
@Entity
public class PickupOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer pickupOptionId;

  @Setter
  @Column(length = 10)
  private String name;

  @Setter @Column private String description;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    PickupOption that = (PickupOption) o;

    if (that.getPickupOptionId() != null) {
      return Objects.equals(getPickupOptionId(), that.getPickupOptionId());
    }

    return Objects.equals(getName(), that.getName())
        && Objects.equals(getDescription(), that.getDescription());
  }

  @Override
  public int hashCode() {
    if (getPickupOptionId() != null) {
      return Objects.hash(getPickupOptionId());
    }

    return Objects.hash(getName(), getDescription());
  }
}
