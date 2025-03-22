package io.jeidiiy.sirenordersystem.jwt.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotSetException extends ClientErrorException {
  public RefreshTokenNotSetException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}
