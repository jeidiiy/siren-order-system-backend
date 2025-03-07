package io.jeidiiy.sirenordersystem.store.repository;

import io.jeidiiy.sirenordersystem.store.domain.StorePickupOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorePickupOptionJpaRepository extends JpaRepository<StorePickupOption, Integer> {}
