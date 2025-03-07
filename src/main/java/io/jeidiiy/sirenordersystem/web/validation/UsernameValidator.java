package io.jeidiiy.sirenordersystem.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

  private static final String USERNAME_PATTERN = "^[a-zA-Z]+$";

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    if (username == null) {
      return false;
    }
    return username.matches(USERNAME_PATTERN);
  }
}
