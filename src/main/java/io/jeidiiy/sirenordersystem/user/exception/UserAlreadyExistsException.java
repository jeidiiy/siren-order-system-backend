package io.jeidiiy.sirenordersystem.user.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ClientErrorException {
  public UserAlreadyExistsException() {
    super(HttpStatus.CONFLICT, "User already exists");
  }

  public UserAlreadyExistsException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}
