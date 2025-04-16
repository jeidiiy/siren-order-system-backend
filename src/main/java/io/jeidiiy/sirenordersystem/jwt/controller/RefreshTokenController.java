package io.jeidiiy.sirenordersystem.jwt.controller;

import static io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter.BEARER_PREFIX;

import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import io.jeidiiy.sirenordersystem.jwt.exception.RefreshTokenNotSetException;
import io.jeidiiy.sirenordersystem.jwt.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리프레시 토큰 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RefreshTokenController {

  private final RefreshTokenService refreshTokenService;

  // 두 값에 required = false 로 설정한 이유는
  // 로그인하지 않은 경우에도(앱 컴포넌트가 마운트될 때) 해당 API 에 요청하게 되는데
  // 이때 예외처리를 별도로 하기 위해서이다.
  // required = ture 인 경우에 오류가 발생하면 스프링에서 예외를 던진다.
  @Operation(summary = "엑세스 토큰 재발급")
  @Parameter(name = "refreshToken", description = "Set-Cookie 로 저장된 리프레시 토큰")
  @PostMapping("/refresh-token")
  public ResponseEntity<Void> refreshToken(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization,
      @CookieValue(value = "refreshToken", required = false) Cookie refreshTokenCookie) {
    // 로그인을 한 번이라도 했다면 Refresh Token 쿠키가 세팅되어 있어야 함.
    if (refreshTokenCookie == null) {
      throw new RefreshTokenNotSetException(ErrorCode.REFRESH_TOKEN_NOT_FOUND_IN_COOKIE);
    }

    // 로그인한 적이 없거나
    // 브라우저를 새로고침해서 액세스 토큰이 초기화된 경우 해당 필드가 비어있음.
    if (authorization == null) {
      authorization = BEARER_PREFIX;
    }

    String accessToken = authorization.substring(BEARER_PREFIX.length()).trim();
    String refreshToken = refreshTokenCookie.getValue().trim();
    String refreshedAccessToken = refreshTokenService.refreshAccessToken(accessToken, refreshToken);

    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + refreshedAccessToken)
        .build();
  }
}
