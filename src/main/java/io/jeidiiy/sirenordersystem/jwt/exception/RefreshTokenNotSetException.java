package io.jeidiiy.sirenordersystem.jwt.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;

public class RefreshTokenNotSetException extends BusinessException {
  public RefreshTokenNotSetException(ErrorCode errorCode) {
    super(errorCode);
  }
}
