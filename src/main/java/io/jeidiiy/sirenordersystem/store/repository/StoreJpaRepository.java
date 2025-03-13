package io.jeidiiy.sirenordersystem.store.repository;

import io.jeidiiy.sirenordersystem.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreJpaRepository extends JpaRepository<Store, Integer> {
  Optional<Store> findByIdAndUserUsername(Integer storeId, String username);
}
