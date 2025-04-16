package io.jeidiiy.sirenordersystem.order.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;

public class NonExistOrderException extends BusinessException {
  public NonExistOrderException(ErrorCode errorCode) {
    super(errorCode);
  }
}
