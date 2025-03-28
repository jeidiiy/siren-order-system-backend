package io.jeidiiy.sirenordersystem.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class IlligalSignatureException extends AuthenticationException {
  public IlligalSignatureException(String msg) {
    super(msg);
  }
}
