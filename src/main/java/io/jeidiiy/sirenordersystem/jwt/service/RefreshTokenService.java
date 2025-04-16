package io.jeidiiy.sirenordersystem.jwt.service;

import io.jeidiiy.sirenordersystem.exception.ErrorCode;
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
    // accessToken 이 비어있다면 스토어 상태 초기화로 인한 요청으로 간주하고 패스
    if (!accessToken.isEmpty() && jwtService.validateToken(accessToken)) {
      throw new ValidAccessTokenException(ErrorCode.REFRESH_TOKEN_VALID_ACCESS_TOKEN);
    }

    // 2. DB 에서 Refresh Token 조회
    RefreshToken storedToken =
        refreshTokenJpaRepository
            .findByToken(refreshToken)
            .orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

    // 3. Refresh Token 만료 체크
    if (jwtService.getExpiration(storedToken.getToken()).before(new Date())) {
      refreshTokenJpaRepository.delete(storedToken); // 만료된 토큰 삭제
      throw new ExpiredRefreshTokenException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
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
