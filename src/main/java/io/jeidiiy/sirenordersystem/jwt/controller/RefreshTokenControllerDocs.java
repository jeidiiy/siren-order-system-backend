package io.jeidiiy.sirenordersystem.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseEntity;

@Tag(name = "리프레시 토큰 API")
public interface RefreshTokenControllerDocs {

  @Operation(summary = "엑세스 토큰 재발급")
  @Parameter(name = "refreshToken", description = "Set-Cookie 로 저장된 리프레시 토큰")
  ResponseEntity<Void> refreshToken(String authorization, Cookie refreshTokenCookie);
}
