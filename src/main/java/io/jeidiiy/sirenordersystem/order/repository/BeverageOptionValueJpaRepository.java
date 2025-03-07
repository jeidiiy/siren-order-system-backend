package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.BeverageOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageOptionValueJpaRepository
    extends JpaRepository<BeverageOptionValue, Integer> {}
