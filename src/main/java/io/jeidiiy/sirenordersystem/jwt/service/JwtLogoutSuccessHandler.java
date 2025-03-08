package io.jeidiiy.sirenordersystem.jwt.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * JWT 로그아웃 성공
 * 핸들러 JWT 로그아웃 성공 시 처리할 로직을 정의합니다.
 * 로그아웃 시 Access Token 과 Refresh Token 을 삭제합니다.
 *
 * @author jeidiiy
 */
@RequiredArgsConstructor
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

  private final RefreshTokenService refreshTokenService;

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    // Access Token 삭제
    if (request.getHeader(HttpHeaders.AUTHORIZATION).startsWith("Bearer")) {
      response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }

    // Refresh Token 삭제
    Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals("refreshToken"))
        .findFirst()
        .ifPresent(
            cookie -> {
              cookie.setMaxAge(0);
              refreshTokenService.deleteByToken(cookie.getValue());
            });

    response.setStatus(HttpServletResponse.SC_OK);
  }
}
