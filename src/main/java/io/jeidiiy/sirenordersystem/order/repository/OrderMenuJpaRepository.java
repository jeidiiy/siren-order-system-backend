package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuJpaRepository extends JpaRepository<OrderMenu, Integer> {}
