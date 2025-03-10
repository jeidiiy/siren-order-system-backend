package io.jeidiiy.sirenordersystem.jwt.repository;

import io.jeidiiy.sirenordersystem.jwt.model.RefreshToken;
import io.jeidiiy.sirenordersystem.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Integer> {
  Optional<RefreshToken> findByToken(String refreshToken);

  Optional<RefreshToken> findByUser(User user);

  void deleteByToken(String token);
}
