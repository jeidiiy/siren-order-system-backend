package io.jeidiiy.sirenordersystem.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  private static final String PASSWORD_PATTERN =
      "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    if (password == null) {
      return false;
    }
    return password.matches(PASSWORD_PATTERN);
  }
}
