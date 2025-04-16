package io.jeidiiy.sirenordersystem.user.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BusinessException {
  public UserAlreadyExistsException(ErrorCode errorCode) {
    super(errorCode);
  }
}
