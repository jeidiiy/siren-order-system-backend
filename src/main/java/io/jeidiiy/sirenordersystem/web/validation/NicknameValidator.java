package io.jeidiiy.sirenordersystem.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {

  private static final String NICKNAME_PATTERN = "^[가-힣]{1,6}$";

  @Override
  public boolean isValid(String nickname, ConstraintValidatorContext constraintValidatorContext) {
    if (nickname == null) { // 아무 값도 입력하지 않으면 realName 을 대신 사용함.
      return true;
    }
    return nickname.matches(NICKNAME_PATTERN);
  }
}
