package io.jeidiiy.sirenordersystem.user.controller;

import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;
import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.domain.dto.*;
import io.jeidiiy.sirenordersystem.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "사용자 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입")
  @PostMapping
  public ResponseEntity<Void> signUp(@RequestBody @Valid UserPostRequestBody userPostRequestBody) {
    userService.signUp(userPostRequestBody);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "본인 정보 조회")
  @PreAuthorize("#username == authentication.name")
  @GetMapping("/{username}")
  public ResponseEntity<UserGetResponseDto> getUserByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserResponseDtoByUsername(username));
  }

  @Operation(summary = "본인 정보 수정")
  @PreAuthorize("#username == authentication.name")
  @PatchMapping("/{username}")
  public ResponseEntity<Void> updateUserByUsername(
      @PathVariable String username,
      @Valid @RequestBody UserPatchRequestBody userPatchRequestBody) {
    userService.updateUserByUsername(username, userPatchRequestBody);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "비밀번호 수정")
  @PreAuthorize("#username == authentication.name")
  @PatchMapping("/{username}/password")
  public ResponseEntity<Void> updatePasswordByUsername(
      @PathVariable String username,
      @Valid @RequestBody UserPasswordPatchRequestBody userPasswordPatchRequestBody) {
    userService.updatePasswordByUsername(username, userPasswordPatchRequestBody);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "회원탈퇴")
  @PreAuthorize("#username == authentication.name")
  @DeleteMapping("/{username}")
  public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
    userService.deleteUserByUsername(username);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "로그인")
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
