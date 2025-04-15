package io.jeidiiy.sirenordersystem.cart.repository;

import io.jeidiiy.sirenordersystem.cart.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartJpaRepository extends JpaRepository<Cart, Integer> {
  @Query("SELECT DISTINCT c FROM Cart c JOIN FETCH c.product WHERE c.user.id = :userId")
  List<Cart> findAllByUserId(Integer userId);

  @Modifying
  @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
  void deleteAllByUserId(Integer userId);
}
