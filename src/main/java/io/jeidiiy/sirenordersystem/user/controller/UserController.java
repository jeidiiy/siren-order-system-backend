package io.jeidiiy.sirenordersystem.user.controller;

import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserLoginRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPatchRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPostRequestBody;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @PreAuthorize("#username == authentication.name")
  @GetMapping("/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserByUsername(username));
  }

  @PreAuthorize("#username == authentication.name")
  @PatchMapping("/{username}")
  public ResponseEntity<Void> updateUserByUsername(
      @PathVariable String username,
      @Valid @RequestBody UserPatchRequestBody userPatchRequestBody) {
    userService.updateUserByUsername(username, userPatchRequestBody);
    return ResponseEntity.ok().build();
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
