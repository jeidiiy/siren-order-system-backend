package io.jeidiiy.sirenordersystem.store.repository;

import io.jeidiiy.sirenordersystem.store.domain.PickupOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupOptionJpaRepository extends JpaRepository<PickupOption, Integer> {}
