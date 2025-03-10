package io.jeidiiy.sirenordersystem.store.domain;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(name = "stores")
@Entity
public class Store {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer storeId;

  @Setter @Column private String name;

  @Setter
  @Column(length = 11)
  private String contactNumber;

  @Setter
  @Column(length = 10)
  private String openAt;

  @Setter
  @Column(length = 10)
  private String closeAt;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Store store = (Store) o;

    if (store.getStoreId() != null) {
      return Objects.equals(getStoreId(), store.getStoreId());
    }

    return Objects.equals(getName(), store.getName())
        && Objects.equals(getContactNumber(), store.getContactNumber())
        && Objects.equals(getOpenAt(), store.getOpenAt())
        && Objects.equals(getCloseAt(), store.getCloseAt());
  }

  @Override
  public int hashCode() {
    if (getStoreId() != null) {
      return Objects.hash(getStoreId());
    }

    return Objects.hash(getName(), getContactNumber(), getOpenAt(), getCloseAt());
  }
}
