package io.jeidiiy.sirenordersystem.user.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends BusinessException {
  public PasswordNotMatchException(ErrorCode errorCode) {
    super(errorCode);
  }
}
