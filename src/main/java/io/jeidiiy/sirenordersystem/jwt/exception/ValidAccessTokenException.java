package io.jeidiiy.sirenordersystem.jwt.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class ValidAccessTokenException extends ClientErrorException {
  public ValidAccessTokenException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
