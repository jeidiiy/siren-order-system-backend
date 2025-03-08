package io.jeidiiy.sirenordersystem.jwt.controller;

import static io.jeidiiy.sirenordersystem.jwt.filter.JwtAuthenticationFilter.BEARER_PREFIX;

import io.jeidiiy.sirenordersystem.jwt.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RefreshTokenController {

  private final RefreshTokenService refreshTokenService;

  @PostMapping("/refresh-token")
  public ResponseEntity<Void> refreshToken(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
      @CookieValue("refreshToken") Cookie refreshTokenCookie) {
    String accessToken = authorization.substring(BEARER_PREFIX.length()).trim();
    String refreshToken = refreshTokenCookie.getValue().trim();

    String refreshedAccessToken = refreshTokenService.refreshAccessToken(accessToken, refreshToken);

    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + refreshedAccessToken)
        .build();
  }
}
