package io.jeidiiy.sirenordersystem.user.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends ClientErrorException {
  public PasswordNotMatchException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}
