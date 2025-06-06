package io.jeidiiy.sirenordersystem.product.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException {
  public ProductNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
