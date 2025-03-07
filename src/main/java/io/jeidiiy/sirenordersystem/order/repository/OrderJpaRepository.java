package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Integer> {}
