package io.jeidiiy.sirenordersystem.user.domain.dto;

import io.jeidiiy.sirenordersystem.user.domain.User;

public record UserGetResponseDto(String username, String realname, String nickname) {
  public static UserGetResponseDto from(User user) {
    return new UserGetResponseDto(user.getUsername(), user.getRealname(), user.getNickname());
  }
}
