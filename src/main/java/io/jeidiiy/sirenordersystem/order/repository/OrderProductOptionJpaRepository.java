package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.OrderProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductOptionJpaRepository extends JpaRepository<OrderProductOption, Integer> {}
