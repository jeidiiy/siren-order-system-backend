package io.jeidiiy.sirenordersystem.store.domain;

import io.jeidiiy.sirenordersystem.store.domain.dto.StorePutRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.*;

@Getter
@ToString
@Table(name = "stores")
@Entity
public class Store {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Setter @Column private String name;

  @Setter @Column private String address;

  @Setter @Column private String imageUrl;

  @Setter
  @Column(length = 11)
  private String contactNumber;

  @Setter
  @Column(length = 10)
  private String openAt;

  @Setter
  @Column(length = 10)
  private String closeAt;

  @Setter @Column private Boolean isOpen;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private List<StorePickupOption> storePickupOptions = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User user;

  public static Store of(
      String name,
      String address,
      String imageUrl,
      String contactNumber,
      String openAt,
      String closeAt,
      Boolean isOpen) {
    Store store = new Store();
    store.setName(name);
    store.setAddress(address);
    store.setImageUrl(imageUrl);
    store.setContactNumber(contactNumber);
    store.setOpenAt(openAt);
    store.setCloseAt(closeAt);
    store.setIsOpen(isOpen);
    return store;
  }

  public void update(StorePutRequestBody requestBody) {
    this.name = requestBody.name();
    this.address = requestBody.address();
    this.imageUrl = requestBody.imageUrl();
    this.contactNumber = requestBody.contactNumber();
    this.openAt = requestBody.openAt();
    this.closeAt = requestBody.closeAt();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Store store = (Store) o;

    if (store.getId() != null) {
      return Objects.equals(getId(), store.getId());
    }

    return Objects.equals(getName(), store.getName())
        && Objects.equals(getAddress(), store.getAddress())
        && Objects.equals(getImageUrl(), store.getImageUrl())
        && Objects.equals(getContactNumber(), store.getContactNumber())
        && Objects.equals(getOpenAt(), store.getOpenAt())
        && Objects.equals(getCloseAt(), store.getCloseAt())
        && Objects.equals(getIsOpen(), store.getIsOpen())
        && Objects.equals(getUser(), store.getUser())
        && Objects.equals(getStorePickupOptions(), store.getStorePickupOptions());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(
        getName(),
        getAddress(),
        getImageUrl(),
        getContactNumber(),
        getOpenAt(),
        getCloseAt(),
        getIsOpen(),
        getUser(),
        getStorePickupOptions());
  }
}
