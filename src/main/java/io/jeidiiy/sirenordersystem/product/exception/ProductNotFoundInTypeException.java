package io.jeidiiy.sirenordersystem.product.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;

public class ProductNotFoundInTypeException extends BusinessException {
  public ProductNotFoundInTypeException(ErrorCode errorCode) {
    super(errorCode);
  }
}
