package io.jeidiiy.sirenordersystem.user.controller;

import io.jeidiiy.sirenordersystem.user.domain.dto.UserLoginRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPostRequestBody;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;

import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<Void> signUp(@RequestBody @Valid UserPostRequestBody userPostRequestBody) {
    userService.signUp(userPostRequestBody);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping // TODO: 테스트용으로 만들어 놓은 API, 추후 변경 필요
  public String hello() {
    return "Hello, World!";
  }

  @PostMapping("/authenticate")
  public ResponseEntity<Void> authenticate(
      @Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
    JwtToken jwtToken = userService.login(userLoginRequestBody);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken())
        .header(
            HttpHeaders.SET_COOKIE, createRefreshTokenCookie(jwtToken.refreshToken()).toString())
        .build();
  }

  private ResponseCookie createRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true) // JavaScript 에서 접근 불가
        .path("/") // 모든 경로에서 사용 가능
        .maxAge(Duration.ofDays(30)) // 30일 만료
        .build();
  }
}
