package io.jeidiiy.sirenordersystem.order.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class NonExistOrderProductException extends ClientErrorException {
  public NonExistOrderProductException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
