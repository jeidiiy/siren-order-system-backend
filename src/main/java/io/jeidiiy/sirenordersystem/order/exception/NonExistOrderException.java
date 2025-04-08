package io.jeidiiy.sirenordersystem.order.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class NonExistOrderException extends ClientErrorException {
  public NonExistOrderException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
