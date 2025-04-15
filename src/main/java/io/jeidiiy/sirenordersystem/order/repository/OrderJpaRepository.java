package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderJpaRepository extends JpaRepository<Order, Integer> {
  @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.store WHERE o.user.id = :userId")
  List<Order> findAllByUserId(Integer userId);

  @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.store WHERE o.id = :orderId AND o.user.id = :userId")
  Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);
}
