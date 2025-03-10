package io.jeidiiy.sirenordersystem.jwt.service;

import io.jeidiiy.sirenordersystem.jwt.exception.ExpiredRefreshTokenException;
import io.jeidiiy.sirenordersystem.jwt.exception.RefreshTokenNotFoundException;
import io.jeidiiy.sirenordersystem.jwt.exception.ValidAccessTokenException;
import io.jeidiiy.sirenordersystem.jwt.model.RefreshToken;
import io.jeidiiy.sirenordersystem.jwt.repository.RefreshTokenJpaRepository;
import io.jeidiiy.sirenordersystem.user.domain.User;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenService {
  private final RefreshTokenJpaRepository refreshTokenJpaRepository;
  private final JwtService jwtService;

  public Optional<RefreshToken> findByUser(User user) {
    return refreshTokenJpaRepository.findByUser(user);
  }

  public void save(RefreshToken refreshToken) {
    refreshTokenJpaRepository.save(refreshToken);
  }

  public String refreshAccessToken(String accessToken, String refreshToken) {
    // 1. Access Token 이 유효한지 확인. 유효하면 예외 발생
    if (jwtService.validateToken(accessToken)) {
      throw new ValidAccessTokenException("액세스 토큰이 유효합니다. 새로운 액세스 토큰을 발급할 수 없습니다.");
    }

    // 2. DB 에서 Refresh Token 조회
    RefreshToken storedToken =
        refreshTokenJpaRepository
            .findByToken(refreshToken)
            .orElseThrow(() -> new RefreshTokenNotFoundException("유효하지 않은 리프레시 토큰입니다."));

    // 3. Refresh Token 만료 체크
    if (jwtService.getExpiration(storedToken.getToken()).before(new Date())) {
      refreshTokenJpaRepository.delete(storedToken); // 만료된 토큰 삭제
      throw new ExpiredRefreshTokenException("리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요.");
    }

    // 4. 유저 정보 가져오기
    User user = storedToken.getUser();

    // 5. 새로운 Access Token 생성
    return jwtService.generateAccessToken(new Date(), user.getUsername());
  }

  public void deleteByToken(String token) {
    refreshTokenJpaRepository.deleteByToken(token);
  }
}
