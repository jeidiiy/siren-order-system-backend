package io.jeidiiy.sirenordersystem.cart.domain;

import io.jeidiiy.sirenordersystem.product.domain.Product;
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
@Table(name = "carts")
@Entity
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  @Setter
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  @Setter
  private Product product;

  @Setter private Integer quantity;

  @Setter private Integer price;

  public static Cart of(User user, Product product) {
    Cart cart = new Cart();
    cart.setUser(user);
    cart.setProduct(product);
    cart.setQuantity(1);
    cart.setPrice(product.getBasePrice());
    return cart;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Cart cart = (Cart) o;

    if (cart.getId() != null) {
      return Objects.equals(id, cart.getId());
    }

    return Objects.equals(getUser(), cart.getUser())
        && Objects.equals(getProduct(), cart.getProduct())
        && Objects.equals(getQuantity(), cart.getQuantity())
        && Objects.equals(getPrice(), cart.getPrice());
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return Objects.hash(getId());
    }

    return Objects.hash(getUser(), getProduct(), getQuantity(), getPrice());
  }
}
