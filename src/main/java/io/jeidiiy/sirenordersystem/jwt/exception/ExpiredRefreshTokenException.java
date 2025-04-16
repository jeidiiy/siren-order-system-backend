package io.jeidiiy.sirenordersystem.jwt.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;

public class ExpiredRefreshTokenException extends BusinessException {
  public ExpiredRefreshTokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
