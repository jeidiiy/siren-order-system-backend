package io.jeidiiy.sirenordersystem.store.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "store_pickup_options")
@Entity
public class StorePickupOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer storePickupOptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  @ToString.Exclude
  private Store store;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pickup_option_id")
  @ToString.Exclude
  private PickupOption pickupOption;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    StorePickupOption that = (StorePickupOption) o;

    if (that.getStorePickupOptionId() != null) {
      return Objects.equals(getStorePickupOptionId(), that.getStorePickupOptionId());
    }

    return Objects.equals(getStore(), that.getStore())
        && Objects.equals(getPickupOption(), that.getPickupOption());
  }

  @Override
  public int hashCode() {
    if (getStorePickupOptionId() != null) {
      return Objects.hash(getStorePickupOptionId());
    }

    return Objects.hash(getStore(), getPickupOption());
  }
}
