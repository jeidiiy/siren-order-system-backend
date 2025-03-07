package io.jeidiiy.sirenordersystem.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RealnameValidator implements ConstraintValidator<Realname, String> {

  private static final String REALNAME_PATTERN = "^[가-힣]{1,6}$";

  @Override
  public boolean isValid(String realName, ConstraintValidatorContext constraintValidatorContext) {
    if (realName == null) {
      return false;
    }
    return realName.matches(REALNAME_PATTERN);
  }
}
