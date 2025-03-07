package io.jeidiiy.sirenordersystem.order.repository;

import io.jeidiiy.sirenordersystem.order.domain.BeverageOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageOptionJpaRepository extends JpaRepository<BeverageOption, Integer> {}
