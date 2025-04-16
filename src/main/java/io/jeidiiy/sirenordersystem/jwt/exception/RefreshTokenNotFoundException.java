package io.jeidiiy.sirenordersystem.jwt.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessException {
  public RefreshTokenNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
