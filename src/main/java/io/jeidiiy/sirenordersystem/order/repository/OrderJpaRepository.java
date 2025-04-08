package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Integer> {
  List<Order> findAllByUserId(Integer userId);

  Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);
}
