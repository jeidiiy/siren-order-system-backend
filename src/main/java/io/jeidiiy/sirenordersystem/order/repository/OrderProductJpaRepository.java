package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Integer> {
    List<OrderProduct> findAllByOrderId(Integer orderId);
}
