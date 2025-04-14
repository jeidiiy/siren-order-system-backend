package io.jeidiiy.sirenordersystem.cart.exception;

import io.jeidiiy.sirenordersystem.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class NonExistCartException extends ClientErrorException {
    public NonExistCartException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
