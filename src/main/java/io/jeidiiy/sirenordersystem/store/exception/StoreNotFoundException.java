package io.jeidiiy.sirenordersystem.store.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class StoreNotFoundException extends BusinessException {
  public StoreNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
