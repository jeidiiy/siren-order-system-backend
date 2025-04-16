package io.jeidiiy.sirenordersystem.order.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;

public class NonExistOrderProductException extends BusinessException {
  public NonExistOrderProductException(ErrorCode errorCode) {
    super(errorCode);
  }
}
