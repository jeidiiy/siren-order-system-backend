package io.jeidiiy.sirenordersystem.cart.exception;

import io.jeidiiy.sirenordersystem.exception.BusinessException;
import io.jeidiiy.sirenordersystem.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class NonExistCartException extends BusinessException {
    public NonExistCartException(ErrorCode errorCode) {
        super(errorCode);
    }
}
