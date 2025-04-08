package io.jeidiiy.sirenordersystem.user.repository;

import io.jeidiiy.sirenordersystem.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
}
