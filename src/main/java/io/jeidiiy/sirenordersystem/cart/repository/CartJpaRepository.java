package io.jeidiiy.sirenordersystem.cart.repository;

import io.jeidiiy.sirenordersystem.cart.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, Integer> {
  List<Cart> findAllByUserId(Integer userId);

  void deleteAllByUserId(Integer userId);
}
