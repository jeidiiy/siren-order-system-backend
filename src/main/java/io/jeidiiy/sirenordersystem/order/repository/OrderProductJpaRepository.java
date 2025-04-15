package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Integer> {
  @Query("SELECT DISTINCT op FROM OrderProduct op JOIN FETCH op.product WHERE op.order.id = :orderId")
  List<OrderProduct> findAllByOrderId(Integer orderId);
}
