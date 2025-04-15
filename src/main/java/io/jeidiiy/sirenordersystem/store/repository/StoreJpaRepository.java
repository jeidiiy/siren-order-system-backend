package io.jeidiiy.sirenordersystem.store.repository;

import io.jeidiiy.sirenordersystem.store.domain.Store;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreJpaRepository extends JpaRepository<Store, Integer> {
  @Query("SELECT DISTINCT s FROM Store s JOIN FETCH s.storePickupOptions spo JOIN FETCH spo.pickupOption WHERE s.id = :storeId AND s.user.username = :username")
  Optional<Store> findByIdAndUserUsername(Integer storeId, String username);

  @Query("SELECT DISTINCT s FROM Store s JOIN FETCH s.storePickupOptions spo JOIN FETCH spo.pickupOption")
  List<Store> findAllWithPickupOptions();

  @Query("SELECT DISTINCT s FROM Store s JOIN FETCH s.storePickupOptions spo JOIN FETCH spo.pickupOption WHERE s.id = :storeId")
  Optional<Store> findByIdWithPickupOptions(Integer storeId);
}
