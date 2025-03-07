package io.jeidiiy.sirenordersystem.store.repository;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<Store, Integer> {}
