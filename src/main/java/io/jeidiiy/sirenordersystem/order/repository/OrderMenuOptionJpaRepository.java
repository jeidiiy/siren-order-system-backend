package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.OrderMenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuOptionJpaRepository extends JpaRepository<OrderMenuOption, Integer> {}
