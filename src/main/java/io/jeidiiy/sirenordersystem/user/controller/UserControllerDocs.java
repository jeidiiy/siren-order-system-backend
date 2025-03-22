package io.jeidiiy.sirenordersystem.user.controller;

import io.jeidiiy.sirenordersystem.user.domain.User;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserLoginRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPatchRequestBody;
import io.jeidiiy.sirenordersystem.user.domain.dto.UserPostRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 API")
public interface UserControllerDocs {

  @Operation(summary = "회원가입")
  ResponseEntity<Void> signUp(@RequestBody @Valid UserPostRequestBody userPostRequestBody);

  @Operation(summary = "본인 정보 조회")
  ResponseEntity<User> getUserByUsername(@PathVariable String username);

  @Operation(summary = "본인 정보 수정")
  ResponseEntity<Void> updateUserByUsername(
      @PathVariable String username, @Valid @RequestBody UserPatchRequestBody userPatchRequestBody);

  @Operation(summary = "회원탈퇴")
  ResponseEntity<Void> deleteUserByUsername(@PathVariable String username);

  @Operation(summary = "로그인")
  ResponseEntity<Void> authenticate(@Valid @RequestBody UserLoginRequestBody userLoginRequestBody);
}
