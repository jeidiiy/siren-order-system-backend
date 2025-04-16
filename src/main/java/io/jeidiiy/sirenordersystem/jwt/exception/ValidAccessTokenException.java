package io.jeidiiy.sirenordersystem.jwt.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ValidAccessTokenException extends BusinessException {
  public ValidAccessTokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
